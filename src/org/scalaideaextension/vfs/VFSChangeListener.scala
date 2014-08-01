package org.scalaideaextension.vfs

import com.intellij.openapi.module.Module
import com.intellij.openapi.vfs.VirtualFileEvent

/**
 * any script extending this trait will listen for file modification events
 *
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
trait VFSChangeListener
{
	def contentsChanged(module: Module, event: VirtualFileEvent)
}
