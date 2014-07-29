package org.scalaideaextension.vfs

import com.intellij.openapi.vfs.VirtualFile

/**
 * @author	kostas.kougios
 *            Date: 29/07/14
 */
object VirtualFileUtils
{
	def isChildOfOrSameAs(parent: VirtualFile, child: VirtualFile): Boolean =
		if (parent == null)
			false
		else
			child match {
				case null => false
				case vf if (vf.getPath == parent.getPath) => true
				case vf => isChildOfOrSameAs(vf.getParent, parent)
			}
}
