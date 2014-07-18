package org.kostaskougios.idea;

import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileManager;

/**
 * @author kostas.kougios
 * Date: 19/07/14
 */
public class FileChangeListener {
	public void init() {
		System.out.println("FileChangeListener init() ");
		VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileAdapter() {
			@Override
			public void contentsChanged(VirtualFileEvent event) {
				System.out.println("changed " + event.getFile());
			}
		});
	}
}
