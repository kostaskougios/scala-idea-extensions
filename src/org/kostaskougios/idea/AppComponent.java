package org.kostaskougios.idea;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author kostas.kougios
 * Date: 19/07/14
 */
public class AppComponent implements ApplicationComponent {
	public AppComponent() {
	}

	public void initComponent() {
		System.out.println("AppComponent init() ");
		new FileChangeListener().init();
	}

	public void disposeComponent() {
		System.out.println("AppComponent dispose() ");
	}

	@NotNull
	public String getComponentName() {
		return "AppComponent";
	}
}
