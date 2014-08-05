package org.kostaskougios.idea.scripts

import java.io.File

import com.googlecode.scalascriptengine._
import com.googlecode.scalascriptengine.classloading.{ClassLoaderConfig, ClassRegistry}
import com.intellij.openapi.components.ApplicationComponent
import org.kostaskougios.idea.GlobalEnable
import org.kostaskougios.idea.diagnostics.Diagnose
import org.kostaskougios.idea.scheduling.Futures
import org.scalaideaextension.environment.{Env, ScriptEnvironment}
import org.scalaideaextension.eventlog.EventLog

/**
 * manages compilation of the scala scripts
 *
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
class ScriptsManager(compilationListeners: Array[CompilationListener]) extends ApplicationComponent with Diagnose
{

	import org.kostaskougios.idea.scripts.ScriptsManager._

	// make dirs if missing
	scriptsRootFolder.mkdirs()

	private val sourcePath = SourcePath(new File(scriptsRootFolder, "src/main/scala"))
	private val libFolder = new File(scriptsRootFolder, "lib")
	private val libs = libFolder.listFiles.filter(_.getName.endsWith(".jar")).toSet
	private val currentClassPath = try {
		ClassPathExtractor.currentClassPath(getClass.getClassLoader)
	} catch {
		case e: Throwable =>
			EventLog.error(this, "error getting the classpath for compilation", e)
			e.printStackTrace()
			Set[File]()
	}
	private val config = Config(
		List(sourcePath),
		currentClassPath ++ libs,
		libs,
		ClassLoaderConfig.Default.copy(enableClassRegistry = false),
		List(
			cv => {
				val registry = new ClassRegistry(getClass.getClassLoader, Set(sourcePath.targetDir))
				EventLog.info(this, s"successfully compiled ${registry.allClasses.size} scripts to version ${cv.version}")
				compilationListeners.foreach(_.compilationCompleted(cv, registry))
			}
		),
		getClass.getClassLoader
	)


	scriptEngine = ScalaScriptEngine.onChangeRefresh(config, 1000)

	override def initComponent() {
		forceRefresh()
	}

	def forceRefresh() {
		Futures.backgroundExecution {
			try {
				scriptEngine.cleanBuild
			} catch {
				case e: Throwable =>
					EventLog.error("Script Compilation Error", "Error compiling classes in script directory:", e)
			}
		}
	}

	override def disposeComponent() {
	}

	override def getComponentName = "ScriptsManager"

	override def diagnose =
		s"""
		   |Compilation class path        :\n${config.compilationClassPaths.mkString("\n")}
		   |ClassLoading class path       :\n${config.classLoadingClassPaths.mkString("\n")}
		   |Scala Source Dirs class path  :\n${config.scalaSourceDirs.mkString("\n")}
		   |Compiled classes output path  :\n${config.targetDirs.mkString("\n")}
		 """.stripMargin
}

object ScriptsManager
{
	private val userHome = System.getProperty("user.home")
	private val scriptsRootFolder = new File(userHome, ".scala-idea-extensions")
	private var scriptEngine: ScalaScriptEngine = _

	def waitInitialCompilation() {
		while (scriptEngine.versionNumber == 0) Thread.sleep(10)
	}

	def script[T, R](className: String)(runner: T => R): Option[R] = {
		if (GlobalEnable.isEnabled) {
			Env.set(ScriptEnvironment(scriptsRootFolder))
			try {
				val script = scriptEngine.get[T](className).newInstance
				Option(runner(script))
			} finally {
				Env.set(null)
			}
		} else None
	}
}