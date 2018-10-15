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
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;

public class TclCompletionProposalLabelProvider
		extends CompletionProposalLabelProvider {
	@Override
	protected String createMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')');

		return nameBuffer.toString();
	}

	@Override
	protected String createOverrideMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')'); // $NON-NLS-1$

		return nameBuffer.toString();
	}
}
