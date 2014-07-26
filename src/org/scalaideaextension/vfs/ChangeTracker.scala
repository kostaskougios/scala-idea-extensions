package org.scalaideaextension.vfs

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * keeps track of changed files per project
 *
 * @author	kostas.kougios
 *            Date: 26/07/14
 */
class ChangeTracker private()
{
	private val m = collection.mutable.HashMap.empty[Project, Set[VirtualFile]].withDefault(_ => Set())

	def track(project: Project, vf: VirtualFile) {
		synchronized {
			m(project) += vf
		}
	}

	def apply(project: Project) = m(project)

	def modifiedFilesSinceLastCall(project: Project): Set[VirtualFile] = synchronized {
		val files = m(project)
		m(project) = Set()
		files
	}
}

object ChangeTracker
{
	def empty = new ChangeTracker
}