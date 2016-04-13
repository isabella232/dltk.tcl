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
package org.eclipse.dltk.tcl.internal.debug.ui.preferences;

import org.eclipse.core.resources.IProject;

import org.eclipse.dltk.debug.ui.preferences.AbstractDebuggingEngineOptionsBlock;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.dltk.tcl.internal.debug.TclDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;

import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Tcl debugging engine preference page
 */
public class TclDebuggingEnginePreferencePage extends AbstractConfigurationBlockPropertyAndPreferencePage {

	static PreferenceKey DEBUGGING_ENGINE = new PreferenceKey(TclDebugPlugin.PLUGIN_ID,
			TclDebugConstants.DEBUGGING_ENGINE_ID_KEY);

	private final String PREFERENCE_PAGE_ID = "org.eclipse.dltk.tcl.preferences.debug.engines"; //$NON-NLS-1$
	private final String PROPERTY_PAGE_ID = "org.eclipse.dltk.tcl.propertyPage.debug.engines"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractDebuggingEngineOptionsBlock(newStatusChangedListener, project, getKeys(), container) {

			@Override
			protected String getNatureId() {
				return TclNature.NATURE_ID;
			}

			@Override
			protected PreferenceKey getSavedContributionKey() {
				return DEBUGGING_ENGINE;
			}
		};
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getProjectHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}

	@Override
	protected void setDescription() {
		setDescription(TclDebugPreferencesMessages.TclDebugEnginePreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(TclDebugPlugin.getDefault().getPluginPreferences()));
	}

	private PreferenceKey[] getKeys() {
		return new PreferenceKey[] { DEBUGGING_ENGINE };
	}

}
