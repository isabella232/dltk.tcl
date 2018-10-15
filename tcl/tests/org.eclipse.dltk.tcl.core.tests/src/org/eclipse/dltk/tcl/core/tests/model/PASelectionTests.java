/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.core.tests.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.tests.model.AbstractModelCompletionTests;

import junit.framework.Test;
import junit.framework.TestCase;

public class PASelectionTests extends AbstractModelCompletionTests {

	private static final String PROJECT_NAME = "PASelection";

	public PASelectionTests(String name) {
		super(Activator.PLUGIN_ID, name);
	}

	@Override
	public void setUpSuite() throws Exception {
		this.PROJECT = this.setUpScriptProject(PROJECT_NAME);
		super.setUpSuite();
		waitUntilIndexesReady();
	}

	@Override
	public void tearDownSuite() throws Exception {
		super.tearDownSuite();
		deleteProject(PROJECT_NAME);
	}

	public static Test suite() {
		return new Suite(PASelectionTests.class);
	}

	private void testDo(String expected, String module, String pattern,
			String project) throws ModelException {
		ISourceModule cu = this.getSourceModule(project, "src", module);

		String str = cu.getSource();
		String completeBehind = pattern;
		int cursorLocation = str.lastIndexOf(completeBehind)
				+ completeBehind.length();
		IModelElement[] elements = cu.codeSelect(cursorLocation, 1);
		TestCase.assertEquals(1, elements.length);
		TestCase.assertEquals(expected, elements[0].getHandleIdentifier());
	}

	public void testSelection001() throws ModelException {
		testDo("=PASelection/pkg1<{module0.tcl~myglobalproc", "test.tcl",
				"myglobal", PROJECT_NAME);
	}

	public void testSelection001b() throws ModelException {
		testDo("=PASelection/pkg1<{module0.tcl[pkgnamespace~myproc", "test.tcl",
				"pkgnamespace::my", PROJECT_NAME);
	}

	public void testSelection002() throws ModelException {
		testDo("=PASelection/pkg2<{module0.tcl~myglobalproc", "test2.tcl",
				"myglobal", PROJECT_NAME);
	}

	public void testSelection002b() throws ModelException {
		testDo("=PASelection/pkg2<{module0.tcl[pkgnamespace~myproc",
				"test2.tcl", "pkgnamespace::my", PROJECT_NAME);
	}

	public void testSelection003() throws ModelException {
		testDo("=PASelection/pkg1<{module0.tcl~myglobalproc", "test3.tcl",
				"myglobal", PROJECT_NAME);
	}

	public void testSelection003b() throws ModelException {
		testDo("=PASelection/pkg1<{module0.tcl[pkgnamespace~myproc",
				"test3.tcl", "pkgnamespace::my", PROJECT_NAME);
	}

	public void testSelection004() throws ModelException {
		testDo("=PASelection/pkg2<{module0.tcl~myglobalproc", "test4.tcl",
				"myglobal", PROJECT_NAME);
	}

	public void testSelection004b() throws ModelException {
		testDo("=PASelection/pkg2<{module0.tcl[pkgnamespace~myproc",
				"test4.tcl", "pkgnamespace::my", PROJECT_NAME);
	}

	public void testSelection005() throws ModelException {
		ISourceModule cu = this.getSourceModule(PROJECT_NAME, "src",
				"test5.tcl");

		String str = cu.getSource();
		String completeBehind = "pkgnamespace::my";
		int cursorLocation = str.lastIndexOf(completeBehind)
				+ completeBehind.length();
		IModelElement[] elements = cu.codeSelect(cursorLocation, 1);
		TestCase.assertEquals(2, elements.length);
	}
}
