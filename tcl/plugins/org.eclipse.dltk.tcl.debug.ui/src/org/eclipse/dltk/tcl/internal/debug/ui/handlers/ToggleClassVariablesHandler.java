/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.handlers;

import org.eclipse.dltk.debug.ui.handlers.AbstractToggleClassVariableHandler;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.dltk.tcl.internal.debug.TclDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Toggles the display of tcl class variables in the debug 'Variables' view
 */
public class ToggleClassVariablesHandler extends
		AbstractToggleClassVariableHandler {

	@Override
	protected String getModelId() {
		return TclDebugConstants.DEBUG_MODEL_ID;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(TclDebugPlugin.getDefault()
				.getPluginPreferences());
	}
}
