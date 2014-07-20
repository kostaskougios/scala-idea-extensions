package org.kostaskougios.idea.scripts

import java.io.File

import com.googlecode.scalascriptengine._
import com.intellij.ide.plugins.cl.PluginClassLoader
import com.intellij.openapi.components.ApplicationComponent
import org.kostaskougios.idea.eventlog.EventLog
import org.kostaskougios.idea.scheduling.Futures

import scala.collection.JavaConverters._

/**
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
class ScriptsManager extends ApplicationComponent
{
	private val userHome = System.getProperty("user.home")
	private val sourcePath = new File(userHome, ".scala-idea-extensions")

	private var compilationListeners = List[CompilationListener]()

	private val config = Config(
		List(SourcePath(sourcePath)),
		currentClassPath,
		Set(),
		ClassLoaderConfig.Default.copy(enableClassRegistry = true),
		compilationListeners = List(
			cv => {
				EventLog.info("Script Compilation", s"successfully compiled ${cv.classLoader.all.size} scripts to version ${cv.version}")
				compilationListeners.foreach(_.compilationCompleted(cv))
			}
		)
	)
	private val scriptEngine = ScalaScriptEngine.onChangeRefresh(config, 1000)

	def registerCompilationListener(listener: CompilationListener) {
		synchronized {
			compilationListeners = listener :: compilationListeners
		}
	}

	override def initComponent() {
		Futures.backgroundExecution {
			try {
				scriptEngine.refresh
			} catch {
				case e: Throwable =>
					EventLog.error("Script Compilation Error", "Error compiling classes in script directory:", e)
			}
		}
	}

	override def disposeComponent() {
	}

	override def getComponentName = "ScriptsManager"

	private def currentClassPath = {
		// this tries to detect the classpath, if it doesn't work
		// for you, please email me or open an issue explaining your
		// usecase.
		def cp(cl: ClassLoader): Set[File] = cl match {
			case ucl: PluginClassLoader =>
				ucl.getUrls.asScala.map(u => new File(u.getFile)).toSet
		}
		val path = try {
			val cl = getClass.getClassLoader
			cp(cl) ++ System.getProperty("java.class.path").split(File.pathSeparator).map(p => new File(p)).toSet
		} catch {
			case e: Throwable =>
				e.printStackTrace()
				Set[File]()
		}
		path
	}

}
