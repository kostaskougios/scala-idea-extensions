package org.scalaideaextension.modules

import com.intellij.openapi.module.{Module, ModuleUtilCore}
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * @author	kostas.kougios
 *            Date: 29/07/14
 */
object ModuleUtils
{
	def moduleForFile(project: Project, file: VirtualFile): Option[Module] = Option(ModuleUtilCore.findModuleForFile(file, project))
}
