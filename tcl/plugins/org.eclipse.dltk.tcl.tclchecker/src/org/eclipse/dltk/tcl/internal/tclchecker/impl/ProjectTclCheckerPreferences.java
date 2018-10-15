/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.internal.tclchecker.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.PreferencesDelegate;
import org.eclipse.dltk.validators.core.ValidatorRuntime;
import org.eclipse.dltk.validators.internal.core.ValidatorsCore;

public class ProjectTclCheckerPreferences extends AbstractTclCheckerPreferences {

	private final PreferencesDelegate delegate;

	/**
	 * @param project
	 */
	public ProjectTclCheckerPreferences(IProject project) {
		this.delegate = new PreferencesDelegate(project);
		initialize();
	}

	@Override
	protected String readConfiguration() {
		return delegate.getString(ValidatorsCore.PLUGIN_ID, ValidatorRuntime.PREF_CONFIGURATION);
	}

	@Override
	protected void writeConfiguration(String value) {
		delegate.setString(ValidatorsCore.PLUGIN_ID, ValidatorRuntime.PREF_CONFIGURATION, value);
	}

	@Override
	public void delete() {
		delegate.setString(ValidatorsCore.PLUGIN_ID, ValidatorRuntime.PREF_CONFIGURATION, null);
	}

}
