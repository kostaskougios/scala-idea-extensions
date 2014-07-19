package org.kostaskougios.idea.eventlog

import java.io.{PrintWriter, StringWriter}

import com.intellij.notification.{Notification, NotificationType, Notifications}
import org.kostaskougios.idea.scheduling.Futures

/**
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
object EventLog
{
	def info(title: String, msg: String) {
		val notification = new Notification("scala-idea-extensions", title, msg, NotificationType.INFORMATION)
		Notifications.Bus.notify(notification)
		Futures.scheduledExecution(2000) {
			notification.expire()
		}
	}

	def error(title: String, msg: String, e: Throwable) {
		val sw = new StringWriter
		val writer = new PrintWriter(sw)
		e.printStackTrace(writer)
		val stackTrace = sw.toString
		Notifications.Bus.notify(new Notification("scala-idea-extensions", title, msg + "\n" + stackTrace, NotificationType.ERROR))
	}
}
