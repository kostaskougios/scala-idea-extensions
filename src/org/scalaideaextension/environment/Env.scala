package org.scalaideaextension.environment

/**
 * gets the environment of the running script
 * @author	kostas.kougios
 *            Date: 01/08/14
 */
object Env
{
	private val tl = new ThreadLocal[ScriptEnvironment]

	def set(env: ScriptEnvironment) {
		tl.set(env)
	}

	def get = tl.get()
}
