/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text.completion;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ContentAssistPreference;

public class TclContentAssistPreference extends ContentAssistPreference {
	static TclContentAssistPreference sDefault;

	@Override
	protected ScriptTextTools getTextTools() {
		return TclUI.getDefault().getTextTools();
	}

	public static ContentAssistPreference getDefault() {
		if (sDefault == null) {
			sDefault = new TclContentAssistPreference();
		}
		return sDefault;
	}
}
