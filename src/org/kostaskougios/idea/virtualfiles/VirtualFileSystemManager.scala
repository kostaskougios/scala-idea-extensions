package org.kostaskougios.idea.virtualfiles

import org.kostaskougios.idea.scripts.ScriptsManager

/**
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
class VirtualFileSystemManager(scriptsManager: ScriptsManager)
{
	scriptsManager.registerCompilationListener {
		codeVersion =>
			println(codeVersion.classLoader.all)
	}
}
