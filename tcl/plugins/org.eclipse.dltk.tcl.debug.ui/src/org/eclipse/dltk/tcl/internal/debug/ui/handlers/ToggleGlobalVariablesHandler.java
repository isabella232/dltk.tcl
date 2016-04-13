/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.handlers;

import org.eclipse.dltk.debug.ui.handlers.AbstractToggleGlobalVariableHandler;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.dltk.tcl.internal.debug.TclDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Toggles the display of tcl global variables in the debug 'Variables' view
 */
public class ToggleGlobalVariablesHandler extends AbstractToggleGlobalVariableHandler {

	@Override
	protected String getModelId() {
		return TclDebugConstants.DEBUG_MODEL_ID;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(TclDebugPlugin.getDefault().getPluginPreferences());
	}
}
