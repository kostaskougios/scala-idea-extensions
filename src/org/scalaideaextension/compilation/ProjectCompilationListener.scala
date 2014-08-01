package org.scalaideaextension.compilation

import com.intellij.openapi.compiler.CompileContext

/**
 * extension scripts extending this trait will automatically
 * be informed about compilation events of projects
 *
 * @author	kostas.kougios
 *            Date: 22/07/14
 */
trait ProjectCompilationListener
{
	def success(context: CompileContext)
}
