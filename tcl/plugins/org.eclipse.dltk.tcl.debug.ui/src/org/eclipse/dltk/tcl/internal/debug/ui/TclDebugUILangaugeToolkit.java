/*******************************************************************************
 * Copyright (c) 2016, 2017 Jae Gangemi and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.dltk.debug.ui.AbstractDebugUILanguageToolkit;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class TclDebugUILangaugeToolkit extends AbstractDebugUILanguageToolkit {

	@Override
	public String getDebugModelId() {
		return TclDebugConstants.DEBUG_MODEL_ID;
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return TclDebugUIPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public String[] getVariablesViewPreferencePages() {
		return new String[] { "org.eclipse.dltk.tcl.preferences.debug.detailFormatters" };
	}
}
