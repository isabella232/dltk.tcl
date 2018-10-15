/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker;

import org.eclipse.dltk.tcl.tclchecker.TclCheckerPlugin;

public final class TclCheckerConstants {
	private TclCheckerConstants() {
	}


	/**
	 * Version of the TclChecker preferences
	 */
	public static final String PREF_VERSION = "tclchecker.preferences.version"; //$NON-NLS-1$

	public static final String TCL_DEVKIT_LOCAL_VARIABLE = "TCLDEVKIT_LOCAL"; //$NON-NLS-1$

	public static final String CONFIGURATION_EXTENSION_POINT_NAME = TclCheckerPlugin.PLUGIN_ID
			+ ".configuration"; //$NON-NLS-1$
}
