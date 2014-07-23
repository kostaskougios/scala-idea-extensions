package org.kostaskougios.idea.scripts

import java.io.File

import com.googlecode.scalascriptengine._
import com.intellij.ide.plugins.cl.PluginClassLoader
import com.intellij.openapi.components.ApplicationComponent
import org.kostaskougios.idea.GlobalEnable
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
	private val scriptsRootFolder = new File(userHome, ".scala-idea-extensions")
	private val sourcePath = SourcePath(new File(scriptsRootFolder, "src/main/scala"))
	private val libFolder = new File(scriptsRootFolder, "lib")
	private val libs = libFolder.listFiles.filter(_.getName.endsWith(".jar")).toSet
	private val config = Config(
		List(sourcePath),
		currentClassPath ++ libs,
		libs,
		ClassLoaderConfig.Default.copy(enableClassRegistry = false),
		List(
			cv => {
				val registry = new ClassRegistry(Set(sourcePath.targetDir))
				EventLog.info(this, s"successfully compiled ${registry.allClasses.size} scripts to version ${cv.version}")
				compilationListeners.foreach(_.compilationCompleted(cv, registry))
			}
		),
		getClass.getClassLoader
	)
	// is is public so that plugins can re-compile scripts
	private val scriptEngine = ScalaScriptEngine.onChangeRefresh(config, 1000)
	private var compilationListeners = List[CompilationListener]()

	def script[T](className: String): Option[T] = if (GlobalEnable.isEnabled)
		Some(scriptEngine.get[T](className).newInstance)
	else None

	def registerCompilationListener(listener: CompilationListener) {
		synchronized {
			compilationListeners = listener :: compilationListeners
		}
	}

	override def initComponent() {
		Futures.backgroundExecution {
			try {
				scriptEngine.deleteAllClassesInOutputDirectory()
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
		// use case.
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
