/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/
package org.eclipse.dltk.tcl.formatter.tests;

import java.util.Map;

import org.eclipse.dltk.tcl.formatter.TclFormatterConstants;
import org.eclipse.dltk.ui.formatter.FormatterException;
import org.eclipse.dltk.ui.formatter.IScriptFormatter;

@SuppressWarnings("nls")
public class CommentsWrapTest extends AbstractTclFormatterTest {

	public void testWrapping1() throws FormatterException {
		String input = joinLines("# 01234567890 01234567890", "set a 1");
		String output = joinLines("# 01234567890", "# 01234567890", "set a 1");
		assertEquals(output, format(input));
	}

	public void testWrapping2() throws FormatterException {
		String input = joinLines("# 01234567890 01234567890",
				"# 01234567890 01234567890", "set a 1");
		String output = joinLines("# 01234567890", "# 01234567890",
				"# 01234567890", "# 01234567890", "set a 1");
		assertEquals(output, format(input));
	}

	@Override
	protected IScriptFormatter createFormatter(Map<String, Object> preferences) {
		if (preferences == null) {
			preferences = TestTclFormatter.createTestingPreferences();
		}
		preferences.put(TclFormatterConstants.WRAP_COMMENTS, true);
		preferences.put(TclFormatterConstants.WRAP_COMMENTS_LENGTH, 20);
		return super.createFormatter(preferences);
	}
}
