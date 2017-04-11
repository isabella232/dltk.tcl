/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The page for setting the editor options.
 */
public final class TclSmartTypingPreferencePage
		extends AbstractConfigurationBlockPreferencePage {

	@Override
	protected void setDescription() {
		String description = org.eclipse.dltk.tcl.internal.ui.preferences.TclPreferencesMessages.TCLSmartTypingConfigurationBlock_typing_tabTitle;
		setDescription(description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(TclUI.getDefault().getPreferenceStore());
	}

	@Override
	protected Label createDescriptionLabel(Composite parent) {
		return null; // no description for new look.
	}

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new TclSmartTypingConfigurationBlock(overlayPreferenceStore);
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return "";
	}

}
