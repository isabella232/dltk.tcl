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

public class CommandSubstitution extends TclElement implements ISubstitution {

	private TclScript script;

	public TclScript getScript() {
		return script;
	}

	public static boolean iAm(ICodeScanner input) {
		int c = input.read();
		if (c == ICodeScanner.EOF)
			return false;
		input.unread();
		return (c == '[');
	}

	@Override
	public boolean readMe(final ICodeScanner input,
			final SimpleTclParser parser) throws TclParseException {
		if (!iAm(input))
			return false;
		setStart(input.getPosition());
		input.read();
		this.script = parser.parse(input, true,
				() -> parser.handleError(new ErrorDescription(
						Messages.CommandSubstitution_0, getStart(),
						input.getPosition(), ErrorDescription.ERROR)));
		setEnd(input.getPosition() - (input.isEOF() ? 0 : 1));
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("["); //$NON-NLS-1$
		sb.append(script);
		sb.append("]"); //$NON-NLS-1$
		return sb.toString();
	}
}
