/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser.raw;

public class MagicBackslashSubstitution extends TclElement
		implements ISubstitution {

	public static boolean iAm(ICodeScanner input) {
		int c = input.read();
		if (c == ICodeScanner.EOF)
			return false;
		if (c != '\\') {
			input.unread();
			return false;
		}
		boolean nl = TclTextUtils.isNewLine(input);
		input.unread();
		return nl;
	}

	@Override
	public boolean readMe(ICodeScanner input, SimpleTclParser parser)
			throws TclParseException {
		if (!iAm(input))
			return false;
		setStart(input.getPosition());
		input.read();
		TclTextUtils.skipNewLine(input);
		int c;
		do {
			c = input.read();
		} while (c != ICodeScanner.EOF && TclTextUtils.isTrueWhitespace(c));
		input.unread();
		setEnd(input.getPosition() - 1);
		return true;
	}
}
