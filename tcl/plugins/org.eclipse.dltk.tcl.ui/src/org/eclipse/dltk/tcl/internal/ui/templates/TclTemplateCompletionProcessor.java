/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.templates;

import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;

/**
 * Tcl template completion processor
 */
public class TclTemplateCompletionProcessor
		extends ScriptTemplateCompletionProcessor {

	private static char[] IGNORE = new char[] { '.' };

	public TclTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext context) {
		super(context);
	}

	@Override
	protected String getContextTypeId() {
		return TclUniversalTemplateContextType.CONTEXT_TYPE_ID;
	}

	@Override
	protected char[] getIgnore() {
		return IGNORE;
	}

	@Override
	protected ScriptTemplateAccess getTemplateAccess() {
		return TclTemplateAccess.getInstance();
	}
}
