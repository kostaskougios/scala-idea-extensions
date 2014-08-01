package org.kostaskougios.idea.scripts

import com.googlecode.scalascriptengine.CodeVersion
import com.googlecode.scalascriptengine.classloading.ClassRegistry

/**
 * components can be informed when script compilation is completed
 *
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
trait CompilationListener
{
	def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry)
}
