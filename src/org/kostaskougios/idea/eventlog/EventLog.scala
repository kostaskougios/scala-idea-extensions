package org.kostaskougios.idea.eventlog

import java.io.{PrintWriter, StringWriter}

import com.intellij.notification.{Notification, NotificationType, Notifications}

/**
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
object EventLog
{
	def info(title: String, msg: String) {
		Notifications.Bus.notify(new Notification("scala-idea-extensions", title, msg, NotificationType.INFORMATION))
	}

	def error(title: String, msg: String, e: Throwable) {
		val sw = new StringWriter
		val writer = new PrintWriter(sw)
		e.printStackTrace(writer)
		val stackTrace = sw.toString
		Notifications.Bus.notify(new Notification("scala-idea-extensions", title, msg + "\n" + stackTrace, NotificationType.ERROR))
	}
}
