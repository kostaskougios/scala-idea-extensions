package org.kostaskougios.idea.scripts

import com.intellij.openapi.components.ApplicationComponent

/**
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
class ScriptsManager extends ApplicationComponent
{
	println(List(1, 2, 3))

	override def initComponent() {
		println("ScriptsManager init()")
	}

	override def disposeComponent() {
	}

	override def getComponentName = "ScriptsManager"
}
