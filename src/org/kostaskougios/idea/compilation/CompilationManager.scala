package org.kostaskougios.idea.compilation

import com.intellij.openapi.compiler.{CompilationStatusListener, CompileContext, CompilerManager}
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
			compilerManager.addCompilationStatusListener(new CompilationStatusListener
			{
				override def fileGenerated(outputRoot: String, relativePath: String) {
					println(s"fileGenerated $outputRoot  , $relativePath")
				}

				override def compilationFinished(aborted: Boolean, errors: Int, warnings: Int, compileContext: CompileContext) = {
					EventLog.info(this, "Compilation finished")
				}
			})
		}
	}
}
