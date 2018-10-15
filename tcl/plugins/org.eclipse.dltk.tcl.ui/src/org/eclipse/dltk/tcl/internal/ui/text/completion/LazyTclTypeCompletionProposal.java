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

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.completion.LazyScriptTypeCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class LazyTclTypeCompletionProposal
		extends LazyScriptTypeCompletionProposal {
	protected static final char[] TYPE_TRIGGERS = new char[] { '.', '\t', '[',
			'(', ' ' };
	protected static final char[] DOC_TYPE_TRIGGERS = new char[] { '#', '}',
			' ', '.' };

	public LazyTclTypeCompletionProposal(CompletionProposal proposal,
			ScriptContentAssistInvocationContext context) {
		super(proposal, context);
	}

	@Override
	protected char[] getDocTriggers() {
		return DOC_TYPE_TRIGGERS;
	}

	@Override
	protected char[] getTypeTriggers() {
		return TYPE_TRIGGERS;
	}

	@Override
	protected void handleSmartTrigger(IDocument document, char trigger,
			int referenceOffset) throws BadLocationException {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isSmartTrigger(char trigger) {
		if (trigger == '$') {
			return true;
		}

		return false;
	}

	@Override
	protected boolean insertCompletion() {
		IPreferenceStore preference = TclUI.getDefault().getPreferenceStore();
		return preference
				.getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
	}
}
