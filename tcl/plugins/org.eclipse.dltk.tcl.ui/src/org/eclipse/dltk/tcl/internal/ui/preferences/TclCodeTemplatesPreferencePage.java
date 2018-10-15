/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.tcl.internal.ui.TclCodeTemplateArea;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.TclUILanguageToolkit;
import org.eclipse.dltk.ui.preferences.CodeTemplatesPreferencePage;

public class TclCodeTemplatesPreferencePage extends CodeTemplatesPreferencePage {

	public TclCodeTemplatesPreferencePage() {
		super(TclUILanguageToolkit.getInstance(), new TclCodeTemplateArea());
		setPreferenceStore(TclUI.getDefault().getPreferenceStore());
	}

}
