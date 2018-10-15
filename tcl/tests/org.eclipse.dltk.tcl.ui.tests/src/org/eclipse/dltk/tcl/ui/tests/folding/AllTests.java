/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui.tests.folding;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.dltk.tcl.ui.tests.folding");
		//$JUnit-BEGIN$
		suite.addTestSuite(TclFoldingTest.class);
		suite.addTest(TclCommentElementTests.suite());
		//$JUnit-END$
		return suite;
	}
}
