package org.kostaskougios.idea.diagnostics

/**
 * provides diagnostic details as a string
 *
 * @author	kostas.kougios
 *            Date: 24/07/14
 */
class DiagnosticsManager(all: Array[Diagnose])
{
	def allDiagnostics = all.map {
		d =>
			s"${d.getClass.getSimpleName}\n--------------------------------------------------\n${d.diagnose}\n"
	}.mkString("\n")
}
