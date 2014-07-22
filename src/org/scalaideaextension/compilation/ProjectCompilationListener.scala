package org.scalaideaextension.compilation

import com.intellij.openapi.compiler.CompileContext

/**
 * @author	kostas.kougios
 *            Date: 22/07/14
 */
trait ProjectCompilationListener
{
	def success(context: CompileContext)
}
