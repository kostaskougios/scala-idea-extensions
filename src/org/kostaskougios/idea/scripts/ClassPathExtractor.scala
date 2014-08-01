package org.kostaskougios.idea.scripts

import java.io.File
import java.net.URL

import com.intellij.ide.plugins.cl.PluginClassLoader

import scala.collection.JavaConverters._
import scala.language.reflectiveCalls

/**
 * a best effort attempt to get the correct classpath for script compilation
 * and execution
 *
 * @author	kostas.kougios
 *            Date: 28/07/14
 */
object ClassPathExtractor
{
	def currentClassPath(classLoader: ClassLoader) =
		classLoader match {
			case pcl: PluginClassLoader =>
				// we need to use reflection cause our classloader has a different class of the same (!)
				val reflCL = pcl.getClass.getClassLoader.asInstanceOf[ {def getUrls: java.util.List[URL]}]
				val rootUrls = reflCL.getUrls.asScala
				urlsToFiles(pcl.getUrls.asScala).toSet ++ urlsToFiles(rootUrls)
		}

	def urlsToFiles(urls: Traversable[URL]) = urls.map(u => new File(u.toURI))
}
