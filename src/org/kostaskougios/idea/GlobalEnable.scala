package org.kostaskougios.idea

import java.util.concurrent.atomic.AtomicBoolean

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import org.kostaskougios.idea.eventlog.EventLog

/**
 * @author	kostas.kougios
 *            Date: 22/07/14
 */
class GlobalEnable extends AnAction
{
	private val enabled = new AtomicBoolean(true)

	def isEnabled = enabled.get

	override def actionPerformed(e: AnActionEvent) {
		enabled.set(!isEnabled)
		if (isEnabled) {
			EventLog.info("scala-idea-extension", "All scripts are ON")
		} else EventLog.info("scala-idea-extension", "All scripts are OFF")
	}
}
