package org.kostaskougios.idea.compilation

import com.googlecode.scalascriptengine.CodeVersion
import com.intellij.openapi.compiler._
import com.intellij.openapi.project.Project
import org.kostaskougios.idea.eventlog.EventLog
import org.kostaskougios.idea.scripts.{ClassRegistry, CompilationListener, ScriptsManager}
import org.scalaideaextension.compilation.ProjectCompilationListener

/**
 * @author	kostas.kougios
 *            Date: 22/07/14
 */
class CompilationManager(scriptsManager: ScriptsManager) extends CompilationListener
{
	private var projectCompilationListeners = List[String]()
	private val sse = scriptsManager.scriptEngine

	scriptsManager.registerCompilationListener(this)

	def projectOpened(project: Project) {
		val compilerManager = CompilerManager.getInstance(project)
		if (compilerManager != null) {
			compilerManager.addAfterTask(new CompileTask
			{
				override def execute(context: CompileContext) = {
					if (context.getMessageCount(CompilerMessageCategory.ERROR) == 0) {
						EventLog.trace(this, "Compilation finished successfully, invoking listeners.")
						projectCompilationListeners.map(sse.newInstance[ProjectCompilationListener](_)).foreach {
							listener =>
								try {
									listener.success(context)
								} catch {
									case e: Throwable => EventLog.error(listener, "Error while executing.", e)
								}
						}
					}
					true
				}
			})
		}
	}

	override def compilationCompleted(codeVersion: CodeVersion, registry: ClassRegistry) {
		projectCompilationListeners = registry.withTypeOf[ProjectCompilationListener].map(_.getName)
		EventLog.info(this, s"Project compilation script listeners : ${projectCompilationListeners.size}")
	}
}
