package org.kostaskougios.idea.virtualfiles

import com.googlecode.scalascriptengine.CodeVersion
import com.googlecode.scalascriptengine.classloading.ClassRegistry
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.{VirtualFileAdapter, VirtualFileEvent, VirtualFileManager}
import org.kostaskougios.idea.scripts.{CompilationListener, ScriptsManager}
import org.scalaideaextension.eventlog.EventLog
import org.scalaideaextension.modules.ModuleUtils
import org.scalaideaextension.vfs.VFSChangeListener

/**
 * manages scripts that require VirtualFile support i.e. modification listeners
 *
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
class VirtualFileSystemManager(projectManager: ProjectManager) extends CompilationListener
{
	private var vfsChangeListeners = List[String]()

	VirtualFileManager.getInstance.addVirtualFileListener(new VirtualFileAdapter()
	{
		override def contentsChanged(event: VirtualFileEvent) = {
			val openProjects = projectManager.getOpenProjects
			for (project <- openProjects) {
				val moduleO = ModuleUtils.moduleForFile(project, event.getFile)

				moduleO.foreach {
					module =>
						vfsChangeListeners.flatMap { className =>
							ScriptsManager.script[VFSChangeListener](className)
						}.foreach {
							listener =>
								listener.contentsChanged(module, event)
						}
				}
			}
		}
	})

	override def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry) = {
		vfsChangeListeners = registry.withTypeOf[VFSChangeListener].map(_.getName)
		EventLog.info(this, s"VFS change script listeners : ${vfsChangeListeners.mkString(",")}")
	}
}
