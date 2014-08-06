package org.kostaskougios.idea.virtualfiles

import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFileEvent
import org.junit.runner.RunWith
import org.kostaskougios.idea.BaseEndToEndSuite
import org.kostaskougios.idea.scripts.ScriptsManager
import org.scalatest.junit.JUnitRunner

import scala.language.reflectiveCalls

/**
 * @author	kostas.kougios
 *            Date: 06/08/14
 */
@RunWith(classOf[JUnitRunner])
class VirtualFileSystemManagerSuite extends BaseEndToEndSuite
{
	test("notified on file change") {
		// create a file
		fixture.addFileToProject("A.text", "class A{}")

		Thread.sleep(1000)
		// and check if the script was notified
		ScriptsManager.script[ {def lastChange: Option[(Module, VirtualFileEvent)]}, Unit]("unittests.scripts.FileNotification")(
			lc =>
				lc.lastChange should be(Some(modules(0)))
		)
	}
}
