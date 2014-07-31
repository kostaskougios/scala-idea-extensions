package org.kostaskougios.idea.preferences

import javax.swing._

import com.intellij.ide.ui.UISettings
import com.intellij.openapi.options.Configurable
import com.intellij.util.ui.UIUtil

/**
 * @author	kostas.kougios
 *            Date: 31/07/14
 */
class SourceFolderPreferences extends Configurable
{
	private var myComponent: JComponent = _
	private var myFontName: JComboBox[String] = _
	private var myFontSize: JComboBox[String] = _
	private var MyDefaultFontButton: JButton = _
	private var myPanel: JPanel = _

	override def getDisplayName = "Scala Idea Extensions"

	override def getHelpTopic = null

	override def isModified = false

	override def createComponent() = {
		val settings = UISettings.getInstance
		myFontName.setModel(new DefaultComboBoxModel(UIUtil.getValidFontNames(false)))
		myFontSize.setModel(new DefaultComboBoxModel(UIUtil.getStandardFontSizes))
		myFontName.setSelectedItem(settings.FONT_FACE)
		myFontSize.setSelectedItem(String.valueOf(settings.FONT_SIZE))
		myComponent
	}

	override def disposeUIResources() {}

	override def apply() {}

	override def reset() {}
}
