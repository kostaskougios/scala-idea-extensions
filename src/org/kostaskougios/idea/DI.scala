package org.kostaskougios.idea

import com.intellij.openapi.application.ApplicationManager

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
 * dependency injection utilities
 *
 * @author	kostas.kougios
 *            Date: 25/07/14
 */
object DI
{
	private val picoContainer = ApplicationManager.getApplication.getPicoContainer

	def inject[T](implicit ct: ClassTag[T]): T = picoContainer.getComponentInstanceOfType(ct.runtimeClass).asInstanceOf[T]

	def injectAll[T](implicit ct: ClassTag[T]): List[T] =
		picoContainer.getComponentInstancesOfType(ct.runtimeClass).asScala.asInstanceOf[List[T]]
}
