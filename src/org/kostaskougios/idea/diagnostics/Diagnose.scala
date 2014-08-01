package org.kostaskougios.idea.diagnostics

/**
 * any component extending this trait will be taken into
 * account when DiagnosticsManager provides diagnostics
 *
 * @author	kostas.kougios
 *            Date: 24/07/14
 */
trait Diagnose
{
	def diagnose: String
}
