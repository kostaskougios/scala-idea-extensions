package org.kostaskougios.idea.compilation

import com.intellij.openapi.compiler._
import com.intellij.openapi.project.Project
import org.kostaskougios.idea.eventlog.EventLog

/**
 * @author	kostas.kougios
 *            Date: 22/07/14
 */
class CompilationManager
{
	def projectOpened(project: Project) {
		val compilerManager = CompilerManager.getInstance(project)
		if (compilerManager != null) {
			compilerManager.addAfterTask(new CompileTask
			{
				override def execute(context: CompileContext) = {
					if (context.getMessageCount(CompilerMessageCategory.ERROR) == 0) {
						EventLog.info("CompilationManager", "Compilation finished successfully")
					}
					true
				}
			})
		}
	}
}
