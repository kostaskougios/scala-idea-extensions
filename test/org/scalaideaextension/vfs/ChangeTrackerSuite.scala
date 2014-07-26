package org.scalaideaextension.vfs

import com.intellij.openapi.project.Project
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
		val project = mock[Project]
		val file1 = mock[VirtualFile]
		val file2 = mock[VirtualFile]
		tracker.track(project, file1)
		tracker.track(project, file2)

		tracker(project) should be(Set(file1, file2))
	}

	test("modifiedFilesSinceLastCall") {
		val tracker = ChangeTracker.empty
		val project = mock[Project]
		val file1 = mock[VirtualFile]
		val file2 = mock[VirtualFile]
		tracker.track(project, file1)
		tracker.track(project, file2)

		tracker.modifiedFilesSinceLastCall(project) should be(Set(file1, file2))
		tracker(project) should be(Set())
	}
}
