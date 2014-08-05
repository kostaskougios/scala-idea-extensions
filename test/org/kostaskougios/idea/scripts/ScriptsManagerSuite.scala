package org.kostaskougios.idea.scripts

import org.junit.runner.RunWith
import org.kostaskougios.idea.BaseEndToEndSuite
import org.scalatest.junit.JUnitRunner

/**
 * @author	kostas.kougios
 *            Date: 04/08/14
 */
@RunWith(classOf[JUnitRunner])
class ScriptsManagerSuite extends BaseEndToEndSuite
{
	test("compiles ok") {
		ScriptsManager.waitInitialCompilation()
		val o = ScriptsManager.script[String => String, String]("unittests.scripts.A")(
			i =>
				i("x")
		)
		o should be(Some("x-OK"))
	}
}
