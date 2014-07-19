package org.kostaskougios.idea.scheduling

import java.util.{Timer, TimerTask}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
 * manages background task running via futures
 *
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
object Futures
{
	private val timer = new Timer("scala-idea-extensions-timer")

	def backgroundExecution[R](f: => R) = Future(f)

	def scheduledExecution(dtMillis: Long)(f: => Unit) {
		timer.schedule(new TimerTask
		{
			override def run() {
				f
			}
		}, dtMillis)
	}
}
