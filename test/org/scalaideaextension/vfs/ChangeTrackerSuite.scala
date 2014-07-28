package org.scalaideaextension.vfs

import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFile
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers}

/**
 * @author	kostas.kougios
 *            Date: 26/07/14
 */
class ChangeTrackerSuite extends FunSuite with Matchers with MockitoSugar
{
	test("tracks") {
		val tracker = ChangeTracker.empty
		val module = mock[Module]
		val file1 = mock[VirtualFile]
		val file2 = mock[VirtualFile]
		tracker.track(module, file1)
		tracker.track(module, file2)

		tracker(module) should be(Set(file1, file2))
	}

	test("modifiedFilesSinceLastCall") {
		val tracker = ChangeTracker.empty
		val module = mock[Module]
		val file1 = mock[VirtualFile]
		val file2 = mock[VirtualFile]
		tracker.track(module, file1)
		tracker.track(module, file2)

		tracker.modifiedFilesSinceLastCall(module) should be(Set(file1, file2))
		tracker(module) should be(Set())
	}

	test("isChangedSinceLastCall") {
		val tracker = ChangeTracker.empty
		val module = mock[Module]
		val file1 = mock[VirtualFile]
		val file2 = mock[VirtualFile]
		tracker.track(module, file1)
		tracker.track(module, file2)

		tracker.isChangedSinceLastCall(module) should be(true)
		tracker.isChangedSinceLastCall(module) should be(false)
	}
}
