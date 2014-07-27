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

	def track(module: Module, vf: VirtualFile) {
		synchronized {
			m(module) += vf
		}
	}

	def apply(module: Module) = m(module)

	def modifiedFilesSinceLastCall(module: Module): Set[VirtualFile] = synchronized {
		val files = m(module)
		m(module) = Set()
		files
	}
}

object ChangeTracker
{
	def empty = new ChangeTracker
}