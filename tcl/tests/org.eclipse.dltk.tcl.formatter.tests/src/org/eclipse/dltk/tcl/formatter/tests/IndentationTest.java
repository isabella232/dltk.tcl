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

import junit.framework.TestSuite;

import org.eclipse.dltk.formatter.tests.ScriptedTest;
import org.eclipse.dltk.tcl.formatter.TclFormatterConstants;
import org.eclipse.dltk.ui.CodeFormatterConstants;

public class IndentationTest extends ScriptedTest {

	@SuppressWarnings("nls")
	public static TestSuite suite() {
		final IndentationTest tests = new IndentationTest();
		tests.setPreference(TclFormatterConstants.FORMATTER_TAB_CHAR,
				CodeFormatterConstants.SPACE);
		tests.setPreference(TclFormatterConstants.FORMATTER_INDENTATION_SIZE,
				new Integer(3));
		tests.setPreference(TclFormatterConstants.INDENT_SCRIPT, true);
		tests.setPreference(TclFormatterConstants.INDENT_AFTER_BACKSLASH, true);
		return tests.createScriptedSuite(FContext.CONTEXT,
				"scripts/indentation-tests.tcl");
	}

}
