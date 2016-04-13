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
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.dltk.debug.ui.AbstractDebugUILanguageToolkit;
import org.eclipse.dltk.tcl.internal.debug.TclDebugConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class TclDebugUILangaugeToolkit extends AbstractDebugUILanguageToolkit {

	public String getDebugModelId() {
		return TclDebugConstants.DEBUG_MODEL_ID;
	}	
	
	public IPreferenceStore getPreferenceStore() {
		return TclDebugUIPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public String[] getVariablesViewPreferencePages() {
		return new String[] { "org.eclipse.dltk.tcl.preferences.debug.detailFormatters" };
	}
}
