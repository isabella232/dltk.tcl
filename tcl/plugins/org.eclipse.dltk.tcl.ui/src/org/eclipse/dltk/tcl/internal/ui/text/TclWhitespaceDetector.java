/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * A tcl aware white space detector.
 */
public class TclWhitespaceDetector implements IWhitespaceDetector {

	@Override
	public boolean isWhitespace(char character) {
		return Character.isWhitespace(character);
	}
}
