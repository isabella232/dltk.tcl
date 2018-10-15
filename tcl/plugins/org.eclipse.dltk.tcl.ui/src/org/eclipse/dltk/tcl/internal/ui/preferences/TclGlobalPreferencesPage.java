/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;

public class TclGlobalPreferencesPage
		extends AbstractConfigurationBlockPreferencePage {

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new TclGlobalConfigurationBlock(overlayPreferenceStore, this);
	}

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		String description = TclPreferencesMessages.TCLGlobalPreferencePage_description;
		setDescription(description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(TclUI.getDefault().getPreferenceStore());
	}

}
