package org.kostaskougios.idea.virtualfiles

import com.googlecode.scalascriptengine.CodeVersion
import com.googlecode.scalascriptengine.classloading.ClassRegistry
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.{VirtualFileAdapter, VirtualFileEvent, VirtualFileManager}
import org.kostaskougios.idea.scripts.{CompilationListener, ScriptsManager}
import org.scalaideaextension.eventlog.EventLog
import org.scalaideaextension.vfs.VFSChangeListener

/**
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
class VirtualFileSystemManager(scriptsManager: ScriptsManager, module: Module) extends CompilationListener with ProjectComponent
{
	private var vfsChangeListeners = List[String]()

	scriptsManager.registerCompilationListener(this)

	override def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry) = {
		vfsChangeListeners = registry.withTypeOf[VFSChangeListener].map(_.getName)
		EventLog.info(this, s"VFS change script listeners : ${vfsChangeListeners.mkString(",")}")
	}

	override def projectOpened() {
	}

	override def projectClosed() {
	}

	override def initComponent() {
		VirtualFileManager.getInstance.addVirtualFileListener(new VirtualFileAdapter()
		{
			override def contentsChanged(event: VirtualFileEvent) = {
				vfsChangeListeners.flatMap { className =>
					scriptsManager.script[VFSChangeListener](className)
				}.foreach {
					listener =>
						listener.contentsChanged(module, event)
				}
			}
		})
	}

	override def disposeComponent() {}

	override def getComponentName = getClass.getName
}
