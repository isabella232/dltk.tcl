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
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class TclParameterRule implements IRule {
	private IToken token;

	public TclParameterRule(IToken token) {
		super();
		this.token = token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		if (scanner.read() == '-') {
			if (Character.isJavaIdentifierStart((char) scanner.read())) {
				for (;;) {
					int c = scanner.read();
					if (!(Character.isJavaIdentifierPart((char) c)))
						break;
				}
				scanner.unread();
				return token;
			} else
				scanner.unread();
		}

		scanner.unread();
		return Token.UNDEFINED;
	}
}