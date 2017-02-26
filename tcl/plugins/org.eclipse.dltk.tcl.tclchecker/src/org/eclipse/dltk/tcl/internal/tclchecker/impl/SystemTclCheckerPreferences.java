/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker.impl;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.validators.core.ValidatorRuntime;
import org.eclipse.dltk.validators.internal.core.ValidatorsCore;

public class SystemTclCheckerPreferences extends AbstractTclCheckerPreferences {

	public SystemTclCheckerPreferences() {
		initialize();
	}

	@Override
	protected String readConfiguration() {
		return ValidatorsCore.getDefault().getPluginPreferences().getString(ValidatorRuntime.PREF_CONFIGURATION);
	}

	@Override
	protected void writeConfiguration(String value) {
		ValidatorsCore.getDefault().getPluginPreferences().setValue(ValidatorRuntime.PREF_CONFIGURATION, value);
		ValidatorsCore.getDefault().savePluginPreferences();
	}

	@Override
	public void delete() {
		writeConfiguration(Util.EMPTY_STRING);
	}

}
