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
	def info(that: Any, msg: String) {
		info(that.getClass.getSimpleName, msg)
	}

	def info(title: String, msg: String) {
		val notification = new Notification("scala-idea-extensions", title, msg, NotificationType.INFORMATION)
		Notifications.Bus.notify(notification)
		Futures.scheduledExecution(2000) {
			notification.expire()
		}
	}

	def error(title: String, msg: String, e: Throwable) {
		e.printStackTrace()
		val sw = new StringWriter
		e.printStackTrace(new PrintWriter(sw))
		val stackTrace = sw.toString
		val notification = new Notification("scala-idea-extensions", title, msg + "\n" + stackTrace, NotificationType.ERROR)
		Notifications.Bus.notify(notification)
	}
}
