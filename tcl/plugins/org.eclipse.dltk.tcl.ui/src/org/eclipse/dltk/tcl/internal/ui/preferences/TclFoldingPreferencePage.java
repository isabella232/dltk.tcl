/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.text.folding.TclFoldingPreferenceBlock;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.text.folding.DefaultFoldingPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.text.folding.IFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;

/**
 * The page for setting the editor options.
 */
public final class TclFoldingPreferencePage
		extends AbstractConfigurationBlockPreferencePage {

	@Override
	protected String getHelpId() {
		// return IScriptHelpContextIds.TCL_EDITOR_PREFERENCE_PAGE;
		return null;
	}

	@Override
	protected void setDescription() {
		// setDescription(PreferencesMessages.EditorPreferencePage_folding_title);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(TclUI.getDefault().getPreferenceStore());
	}

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new DefaultFoldingPreferenceConfigurationBlock(
				overlayPreferenceStore, this) {
			@Override
			protected IFoldingPreferenceBlock createSourceCodeBlock(
					OverlayPreferenceStore store, PreferencePage page) {
				return new TclFoldingPreferenceBlock(store, page);
			}
		};
	}
}
