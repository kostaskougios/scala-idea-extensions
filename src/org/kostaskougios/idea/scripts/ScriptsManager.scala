package org.kostaskougios.idea.scripts

import java.io.File
import java.net.URL

import com.googlecode.scalascriptengine._
import com.googlecode.scalascriptengine.classloading.{ClassLoaderConfig, ClassRegistry}
import com.intellij.ide.plugins.cl.PluginClassLoader
import com.intellij.openapi.components.ApplicationComponent
import org.kostaskougios.idea.GlobalEnable
import org.kostaskougios.idea.diagnostics.Diagnose
import org.kostaskougios.idea.scheduling.Futures
import org.scalaideaextension.eventlog.EventLog

import scala.collection.JavaConverters._
import scala.language.reflectiveCalls

/**
 * @author	kostas.kougios
 *            Date: 19/07/14
 */
class ScriptsManager extends ApplicationComponent with Diagnose
{
	private val userHome = System.getProperty("user.home")
	private val scriptsRootFolder = new File(userHome, ".scala-idea-extensions")

	// make dirs if missing
	scriptsRootFolder.mkdirs()

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
				val registry = new ClassRegistry(getClass.getClassLoader, Set(sourcePath.targetDir))
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
				// we need to use reflection cause our classloader has a different class of the same (!)
				val reflCL = ucl.getClass.getClassLoader.asInstanceOf[ {def getUrls: java.util.List[URL]}]
				val rootJars = reflCL.getUrls.asScala.map(u => new File(u.getFile))
				ucl.getUrls.asScala.map(u => new File(u.getFile)).toSet ++ rootJars
		}
		val path = try {
			val cl = getClass.getClassLoader
			//			val systemCP = System.getProperty("java.class.path").split(File.pathSeparator)
			val fullCP = cp(cl) // ++ systemCP.map(p => new File(p)).toSet
			fullCP
		} catch {
			case e: Throwable =>
				e.printStackTrace()
				Set[File]()
		}
		path
	}

	override def diagnose =
		s"""
		   |Compilation class path        :\n${config.compilationClassPaths.mkString("\n")}
		   |ClassLoading class path       :\n${config.classLoadingClassPaths.mkString("\n")}
		   |Scala Source Dirs class path  :\n${config.scalaSourceDirs.mkString("\n")}
		   |Compiled classes output path  :\n${config.targetDirs.mkString("\n")}
		 """.stripMargin
}
