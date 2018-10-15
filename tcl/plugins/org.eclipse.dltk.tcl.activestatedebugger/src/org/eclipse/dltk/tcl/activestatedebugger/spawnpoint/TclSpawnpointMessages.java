/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
package org.eclipse.dltk.tcl.activestatedebugger.spawnpoint;

import org.eclipse.osgi.util.NLS;

public class TclSpawnpointMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.activestatedebugger.spawnpoint.TclSpawnpointMessages"; //$NON-NLS-1$
	public static String participantMarkerMessage_commandPlurar;
	public static String participantMarkerMessage_commandSingular;
	public static String participantMarkerMessage_template;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, TclSpawnpointMessages.class);
	}

	private TclSpawnpointMessages() {
	}
}
