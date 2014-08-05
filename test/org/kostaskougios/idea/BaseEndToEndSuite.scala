package org.kostaskougios.idea

import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

/**
 * @author	kostas.kougios
 *            Date: 05/08/14
 */
class BaseEndToEndSuite extends FunSuite with Matchers with BeforeAndAfterAll
{
	var setup: TestSetup = _

	override protected def beforeAll() {
		setup = new TestSetup(getClass.getSimpleName)
		setup.init()
	}

	override protected def afterAll() {
		setup.done()
	}
}
