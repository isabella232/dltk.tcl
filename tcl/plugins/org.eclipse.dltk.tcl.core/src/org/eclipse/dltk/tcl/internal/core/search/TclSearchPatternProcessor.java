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
 *     xored software, Inc. - initial API and Implementation (Yuri Strot)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core.search;

import org.eclipse.dltk.core.search.SearchPatternProcessor;

public class TclSearchPatternProcessor extends SearchPatternProcessor {

	@Override
	public char[] extractDeclaringTypeQualification(String patternString) {
		int pos1 = patternString.lastIndexOf("::");
		if (pos1 != -1) {
			String p = patternString.substring(0, pos1);
			int pos2 = p.lastIndexOf("::");
			if (pos2 != -1) {
				return patternString.substring(0, pos2).toCharArray();
			}
			return null;
		}
		return null;
	}

	@Override
	public char[] extractDeclaringTypeSimpleName(String patternString) {
		int pos1 = patternString.lastIndexOf("::");
		if (pos1 != -1) {
			String p = patternString.substring(0, pos1);
			return getLastTclNameElement(p).toCharArray();
		}
		return null;
	}

	@Override
	public char[] extractSelector(String patternString) {
		return getLastTclNameElement(patternString).toCharArray();
	}

	private String getLastTclNameElement(String patternString) {
		int pos = patternString.lastIndexOf("::");
		if (pos != -1) {
			return patternString.substring(pos + 2);
		}
		return patternString;
	}

	@Override
	public String getDelimiterReplacementString() {
		return SEPARATOR;
	}

	private static final String SEPARATOR = "::";

	@Override
	public ITypePattern parseType(String patternString) {
		int pos = patternString.lastIndexOf(SEPARATOR);
		if (pos != -1) {
			return new TypePattern(
					patternString.substring(0, pos).replace(SEPARATOR,
							TYPE_SEPARATOR_STR),
					patternString.substring(pos + SEPARATOR.length()));
		} else {
			return new TypePattern(null, patternString);
		}
	}
}
