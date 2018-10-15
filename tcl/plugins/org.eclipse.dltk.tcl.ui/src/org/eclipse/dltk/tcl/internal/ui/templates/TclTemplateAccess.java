/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.templates;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Provides access to Tcl template store.
 */
public class TclTemplateAccess extends ScriptTemplateAccess {
	// Template
	private static final String CUSTOM_TEMPLATES_KEY = "org.eclipse.tcl.Templates";

	private static TclTemplateAccess instance;

	public static synchronized TclTemplateAccess getInstance() {
		if (instance == null) {
			instance = new TclTemplateAccess();
		}

		return instance;
	}

	@Override
	protected String getContextTypeId() {
		return TclUniversalTemplateContextType.CONTEXT_TYPE_ID;
	}

	@Override
	protected String getCustomTemplatesKey() {
		return CUSTOM_TEMPLATES_KEY;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return TclUI.getDefault().getPreferenceStore();
	}
}
