package org.kostaskougios.idea.scripts

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

/**
 * @author	kostas.kougios
 *            Date: 04/08/14
 */
@RunWith(classOf[JUnitRunner])
class ScriptsManagerSuite extends FunSuite with Matchers with BeforeAndAfter
{
	before {
		new TestT(getClass.getSimpleName).init()
	}

	test("compiles ok") {
		val scriptManager = new ScriptsManager(Array())
		val o = ScriptsManager.script[String => String, String]("unittests.scripts.A")(
			i =>
				i("x")
		)
		o should be(Some("x-OK"))
	}
}
