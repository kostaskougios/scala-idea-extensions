package org.kostaskougios.idea.scripts

import java.net.URL

import com.intellij.ide.plugins.cl.PluginClassLoader
import org.scalatest.{FunSuite, Matchers}

import scala.collection.JavaConverters._

/**
 * @author	kostas.kougios
 *            Date: 28/07/14
 */
class ClassPathExtractorSuite extends FunSuite with Matchers
{

	class WithUrl(urls: List[URL]) extends PluginClassLoader(Nil.asJava, Array(), null, "", null)
	{
		override def getUrls: java.util.List[URL] = urls.asJava
	}

	test("fixes %20") {
		val files = ClassPathExtractor.urlsToFiles(
			List(
				new URL("file:///tmp/with%20space/file1")
			)
		)
		files.head.getAbsolutePath should be("/tmp/with space/file1")
	}
}
