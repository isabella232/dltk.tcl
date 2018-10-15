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

import java.util.ArrayList;
import java.util.List;

public class QuotesSubstitution extends TclElement
		implements ISubstitution, IWordSubstitution {

	private final List<Object> contents = new ArrayList<>();

	public List<Object> getContents() {
		return contents;
	}

	public static boolean iAm(ICodeScanner scanner) {
		int c = scanner.read();
		if (c == ICodeScanner.EOF) {
			return false;
		}
		scanner.unread();
		return (c == '"');
	}

	@Override
	public boolean readMe(ICodeScanner input, SimpleTclParser parser)
			throws TclParseException {
		if (!iAm(input))
			return false;
		setStart(input.getPosition());
		input.read();
		final TclWordBuffer buffer = new TclWordBuffer();
		while (true) {
			ISubstitution s = parser.getCVB(input);

			if (s != null) {
				s.readMe(input, parser);
				buffer.add(s);
			} else {
				int c = input.read();
				if (c == ICodeScanner.EOF) {
					parser.handleError(new ErrorDescription(
							Messages.QuotesSubstitution_1, getStart(),
							input.getPosition(), ErrorDescription.ERROR));
					break;
				}
				if (c == '"') {
					break;
				}
				buffer.add((char) c);
			}
		}
		contents.addAll(buffer.getContents());
		if (!input.isEOF()) {
			/*
			 * c = input.read(); if (!TclTextUtils.isWhitespace(c) && ( c !=
			 * CodeScanner.EOF) && (c != ']') && (c != ';')) { boolean cont =
			 * SimpleTclParser.handleError(new
			 * ErrorDescription("extra characters after closing-quote",
			 * input.getPosition(), ErrorDescription.ERROR)); if (!cont) throw
			 * new TclParseException("extra characters after closing-quote",
			 * input.getPosition()); do { c = input.read(); } while (c != -1 &&
			 * !TclTextUtils.isWhitespace(c)); input.unread(); } else
			 * input.unread();
			 */
			setEnd(input.getPosition() - 1);
		} else
			setEnd(input.getPosition());

		return true;
	}
}
