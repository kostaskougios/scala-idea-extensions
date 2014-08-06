package org.kostaskougios.idea

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.ProjectManager
import org.kostaskougios.idea.scripts.ScriptsManager
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

/**
 * @author	kostas.kougios
 *            Date: 05/08/14
 */
class BaseEndToEndSuite extends FunSuite with Matchers with BeforeAndAfterAll
{
	var setup: TestSetup = _

	def fixture = setup.fixture

	// all open projects
	def projects = ProjectManager.getInstance().getOpenProjects

	// all modules for the 1st open project
	def modules = ModuleManager.getInstance(projects(0)).getModules

	// override to not wait for compilation
	def waitInitialCompilation = true

	override protected def beforeAll() {
		setup = new TestSetup(getClass.getSimpleName)
		setup.init()
		if (waitInitialCompilation) ScriptsManager.waitInitialCompilation()

	}

	override protected def afterAll() {
		setup.done()
	}

}
