package org.kostaskougios.idea.virtualfiles

import com.googlecode.scalascriptengine.CodeVersion
import com.intellij.openapi.vfs.{VirtualFileAdapter, VirtualFileEvent, VirtualFileManager}
import org.kostaskougios.idea.eventlog.EventLog
import org.kostaskougios.idea.scripts.{ClassRegistry, CompilationListener, ScriptsManager}
import org.scalaideaextension.vfs.VFSChangeListener

/**
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
class VirtualFileSystemManager(scriptsManager: ScriptsManager) extends CompilationListener
{
	private var vfsChangeListeners = List[String]()

	scriptsManager.registerCompilationListener(this)

	VirtualFileManager.getInstance.addVirtualFileListener(new VirtualFileAdapter()
	{
		override def contentsChanged(event: VirtualFileEvent) = {
			EventLog.trace(s"file changed : ${event.getFileName}")

			vfsChangeListeners.flatMap { className =>
				scriptsManager.script[VFSChangeListener](className)
			}.foreach {
				listener =>
					listener.contentsChanged(event)
			}
		}
	})

	override def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry) = {
		vfsChangeListeners = registry.withTypeOf[VFSChangeListener].map(_.getName)
		EventLog.info(this, s"VFS change script listeners : ${vfsChangeListeners.size}")
	}
}
