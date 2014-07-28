package org.kostaskougios.idea.scripts

import java.io.File
import java.net.URL

import com.intellij.ide.plugins.cl.PluginClassLoader

import scala.collection.JavaConverters._
import scala.language.reflectiveCalls

/**
 * @author	kostas.kougios
 *            Date: 28/07/14
 */
object ClassPathExtractor
{
	def currentClassPath(classLoader: ClassLoader) = {
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
		cp(classLoader)
	}
}
