package org.kostaskougios.idea.diagnostics

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import org.kostaskougios.idea.DI
import org.scalaideaextension.eventlog.EventLog

/**
 * the menu to dump diagnostics into the event log of intellij
 *
 * @author	kostas.kougios
 *            Date: 24/07/14
 */
class DiagnosticsMenu extends AnAction
{
	override def actionPerformed(e: AnActionEvent) {
		val diagnosticsManager = DI.inject[DiagnosticsManager]
		EventLog.info(this, diagnosticsManager.allDiagnostics)
	}
}
