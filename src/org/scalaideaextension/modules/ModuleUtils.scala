package org.scalaideaextension.modules

import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFile
import org.scalaideaextension.vfs.VirtualFileUtils

/**
 * @author	kostas.kougios
 *            Date: 29/07/14
 */
object ModuleUtils
{
	def isModulesFile(module: Module, file: VirtualFile): Boolean = VirtualFileUtils.isChildOfOrSameAs(module.getModuleFile, file)
}
