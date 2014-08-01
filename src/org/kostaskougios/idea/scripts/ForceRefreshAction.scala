package org.kostaskougios.idea.scripts

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import org.kostaskougios.idea.DI
import org.scalaideaextension.eventlog.EventLog

/**
 * @author	kostas.kougios
 *            Date: 01/08/14
 */
class ForceRefreshAction extends AnAction
{
	override def actionPerformed(e: AnActionEvent) {
		val scriptsManager = DI.inject[ScriptsManager]
		EventLog.info(this, "triggering recompilation of all scripts")
		scriptsManager.forceRefresh()
	}
}
