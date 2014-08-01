package org.scalaideaextension.vfs

import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFile

/**
 * keeps track of changed files per project
 *
 * @author	kostas.kougios
 *            Date: 26/07/14
 */
class ChangeTracker private()
{
	private val m = collection.mutable.HashMap.empty[Module, Set[VirtualFile]].withDefault(_ => Set())

	/**
	 * tracks a file for a module
	 * @param module	the Module
	 * @param vf		the file to track
	 */
	def track(module: Module, vf: VirtualFile) {
		synchronized {
			m(module) += vf
		}
	}

	/**
	 * @param module	the Module
	 * @return			a set of all files that were tracked for that module
	 */
	def apply(module: Module) = m(module)

	/**
	 * returns all the tracked files for the module and clears those.
	 *
	 * @param module	the module
	 * @return			a set of files
	 */
	def trackedFilesSinceLastCall(module: Module): Set[VirtualFile] = synchronized {
		val files = m(module)
		m(module) = Set()
		files
	}

	/**
	 * returns true if there was at least 1 tracked file for the module and clears the tracked
	 * files for that module
	 *
	 * @param module	the module
	 * @return			true if there was at list 1 file tracked for this module,
	 */
	def isTrackedSinceLastCall(module: Module): Boolean = !trackedFilesSinceLastCall(module).isEmpty
}

object ChangeTracker
{
	/**
	 * @return	an empty ChangeTracker
	 */
	def empty = new ChangeTracker
}