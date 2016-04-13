/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc.  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.debug.ui.preferences.AbstractDebuggingOptionsBlock;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.dltk.tcl.internal.debug.TclDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.util.SWTFactory;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Tcl debug preference page
 */
public class TclDebugPreferencePage extends AbstractConfigurationBlockPropertyAndPreferencePage {

	private static final PreferenceKey BREAK_ON_FIRST_LINE = new PreferenceKey(TclDebugPlugin.PLUGIN_ID,
			DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);

	private static final PreferenceKey ENABLE_DBGP_LOGGING = new PreferenceKey(TclDebugPlugin.PLUGIN_ID,
			DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);

	private static final PreferenceKey STREAM_FILTER_COMMAND_RENAME_WARNING = new PreferenceKey(
			TclDebugPlugin.PLUGIN_ID, TclDebugConstants.DEBUG_STREAM_FILTER_COMMAND_RENAME_WARNING);

	private static final String PREFERENCE_PAGE_ID = "org.eclipse.dltk.tcl.preferences.debug"; //$NON-NLS-1$
	private static final String PROPERTY_PAGE_ID = "org.eclipse.dltk.tcl.propertyPage.debug"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractDebuggingOptionsBlock(newStatusChangedListener, project, getKeys(), container) {

			@Override
			protected PreferenceKey getBreakOnFirstLineKey() {
				return BREAK_ON_FIRST_LINE;
			}

			@Override
			protected PreferenceKey getDbgpLoggingEnabledKey() {
				return ENABLE_DBGP_LOGGING;
			}

			/*
			 * @see AbstractDebuggingOptionsBlock #createSettingsGroup(Group)
			 */
			@Override
			protected void createSettingsGroup(Group group) {
				super.createSettingsGroup(group);
				final Button ignoreRename = SWTFactory.createCheckButton(group,
						TclDebugPreferencesMessages.TclDebugPreferencePage_StreamFilterCommandRename, null, false, 1);
				bindControl(ignoreRename, STREAM_FILTER_COMMAND_RENAME_WARNING, null);
			}
		};
	}

	@Override
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

	protected PreferenceKey[] getKeys() {
		return new PreferenceKey[] { BREAK_ON_FIRST_LINE, ENABLE_DBGP_LOGGING, STREAM_FILTER_COMMAND_RENAME_WARNING };
	}

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}

	@Override
	protected void setDescription() {
		setDescription(TclDebugPreferencesMessages.TclDebugPreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(TclDebugPlugin.getDefault().getPluginPreferences()));
	}
}
