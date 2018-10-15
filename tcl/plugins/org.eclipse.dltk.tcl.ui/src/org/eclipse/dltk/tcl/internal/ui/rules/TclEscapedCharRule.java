/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class TclEscapedCharRule implements IPredicateRule {

	IToken token;
	char esc;

	public TclEscapedCharRule(IToken token, char escape) {
		this.token = token;
		esc = escape;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (c == esc) {
			scanner.read();
			return getSuccessToken();
		} else
			scanner.unread();
		return Token.UNDEFINED;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}
}
