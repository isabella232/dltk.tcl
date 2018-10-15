/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.actions;

import org.eclipse.osgi.util.NLS;

public class ActionMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.internal.debug.ui.actions.ActionMessages";//$NON-NLS-1$

	public static String ToggleSpawnpointAction_0;
	public static String ToggleSpawnpointAction_1;
	public static String ToggleSpawnpointAction_2;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
	}

}
