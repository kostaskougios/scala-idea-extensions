package org.scalaideaextension.vfs

import com.intellij.openapi.vfs.VirtualFileEvent

/**
 * @author	kostas.kougios
 *            Date: 20/07/14
 */
trait VFSChangeListener
{
	def contentsChanged(event: VirtualFileEvent)
}
