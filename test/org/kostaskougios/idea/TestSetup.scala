package org.kostaskougios.idea

import java.io.File

import com.intellij.testFramework.fixtures.JavaCodeInsightFixtureTestCase

/**
 * @author	kostas.kougios
 *            Date: 05/08/14
 */
class TestSetup(name: String) extends JavaCodeInsightFixtureTestCase
{

	override def getName = name

	def init() {
		setUp()
	}

	override def getHomePath = {
		val tmpDir = System.getProperty("java.io.tmpdir")
		val home = new File(tmpDir, "idea-tests-home-path")
		home.mkdir()
		home.getAbsolutePath
	}
}
