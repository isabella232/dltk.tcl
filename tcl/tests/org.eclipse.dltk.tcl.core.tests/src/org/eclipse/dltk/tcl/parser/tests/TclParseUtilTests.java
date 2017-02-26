/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.dltk.tcl.core.TclParseUtil;
import org.junit.Test;

public class TclParseUtilTests {

	@Test
	public void testEscapeName() {
		assertEquals("A", TclParseUtil.escapeName("A"));
		assertEquals("\\u0A", TclParseUtil.escapeName("\n"));
		assertEquals("{A }", TclParseUtil.escapeName("A "));
		assertEquals("{ A }", TclParseUtil.escapeName(" A "));
		assertEquals("{ }", TclParseUtil.escapeName(" "));
		assertEquals("Hello world",
				TclParseUtil.escapeName("Hello\\" + "\n" + " world"));
	}

}
