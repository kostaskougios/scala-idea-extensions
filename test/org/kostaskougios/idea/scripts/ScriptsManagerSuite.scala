package org.kostaskougios.idea.scripts

import org.junit.runner.RunWith
import org.kostaskougios.idea.BaseSuite
import org.scalatest.junit.JUnitRunner

/**
 * @author	kostas.kougios
 *            Date: 04/08/14
 */
@RunWith(classOf[JUnitRunner])
class ScriptsManagerSuite extends BaseSuite
{
	test("compiles ok") {
		val scriptManager = new ScriptsManager(Array())
		val o = ScriptsManager.script[String => String, String]("unittests.scripts.A")(
			i =>
				i("x")
		)
		o should be(Some("x-OK"))
	}
}
