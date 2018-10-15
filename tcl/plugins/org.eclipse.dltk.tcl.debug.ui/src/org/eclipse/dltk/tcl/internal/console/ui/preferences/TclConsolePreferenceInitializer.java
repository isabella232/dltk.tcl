/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.tcl.console.TclConsoleConstants;
import org.eclipse.dltk.tcl.internal.debug.ui.TclDebugUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;


public class TclConsolePreferenceInitializer extends AbstractPreferenceInitializer {

	public TclConsolePreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = TclDebugUIPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(TclConsoleConstants.PREF_NEW_PROMPT,
				TclConsoleConstants.DEFAULT_NEW_PROMPT);
		store.setDefault(TclConsoleConstants.PREF_CONTINUE_PROMPT,
				TclConsoleConstants.DEFAULT_CONTINUE_PROMPT);
	}

}
