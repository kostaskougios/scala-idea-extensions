package org.kostaskougios.idea.scripts

import com.googlecode.scalascriptengine.CodeVersion

/**
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
trait CompilationListener
{
	def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry)
}