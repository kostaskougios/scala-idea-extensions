package org.scalaideaextension.modules

import com.intellij.openapi.module.{Module, ModuleUtilCore}
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * utility methods for Modules
 *
 * @author	kostas.kougios
 *            Date: 29/07/14
 */
object ModuleUtils
{
	/**
	 * finds the module for the provided file
	 *
	 * @param project		the project to look for modules
	 * @param file			the file for which the module will be returned
	 * @return				Some(Module) or None if no module was found for this project and this file
	 */
	def moduleForFile(project: Project, file: VirtualFile): Option[Module] = Option(ModuleUtilCore.findModuleForFile(file, project))
}
