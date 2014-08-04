package org.kostaskougios.idea.scripts

import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory
import org.scalatest.{FunSuite, Matchers}

/**
 * @author	kostas.kougios
 *            Date: 04/08/14
 */
class ScriptsManagerSuite extends FunSuite with Matchers
{
	val factory = IdeaTestFixtureFactory.getFixtureFactory
	val builder = factory.createFixtureBuilder("ScriptsManagerSuite")
	val insightFixture = factory.createCodeInsightFixture(builder.getFixture)
	insightFixture.setTestDataPath("/tmp/ScriptsManagerSuite")

	val scriptManager = new ScriptsManager(Array())

	test("compiles ok") {
		val o = ScriptsManager.script[String => String, String]("unittests.scripts.A")(
			i =>
				i("x")
		)
		o should be(Some("x-OK"))
	}
}
