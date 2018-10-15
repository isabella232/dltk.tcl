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

package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class IfCommandTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() {
		String source = "if {} {set a 20}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test002() {
		String source = "if {} then {set a 20}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test003() {
		String source = "if {} {set a 20} elseif {} {set a 20}";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test004() {
		String source = "if {} {set a 20} elseif {} then {set a 20}";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test005() {
		String source = "if {} {set a 20} elseif {} {set a 20} elseif {} {set a 20}";
		typedCheck(source, 0, 3);
	}

	@Test
	public void test006() {
		String source = "if {} {set a 20} elseif {} {set a 20} else {set a 20}";
		typedCheck(source, 0, 3);
	}

	@Test
	public void test007() {
		String source = "if {} {set a 20} elseif {} {set a 20} else {set a 20}";
		typedCheck(source, 0, 3);
	}

	@Test
	public void test008() {
		String source = "if {} {set a 20} {set a 20}";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test009() {
		String source = "if {} {set a 20} else {set a 20} else {set a 20}";
		typedCheck(source, 1, 2);
	}

	@Test
	public void test010() {
		String source = "if {} {set a 20} elseif";
		typedCheck(source, 1, 1);
	}

	@Test
	public void test011() {
		String source = "if {} {set a 20} elseif {}";
		typedCheck(source, 1, 1);
	}

	@Test
	public void test012() {
		String source = "if {$a} then {} else if";
		typedCheck(source, 1, 2);
	}

	private void typedCheck(String source, int errs, int code) {
		processor = DefinitionManager.getInstance().createProcessor();
		TclParser parser = TestUtils.createParser();
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(1, module.size());
		TclCommand tclCommand = module.get(0);
		EList<TclArgument> arguments = tclCommand.getArguments();
		int scripts = 0;
		for (TclArgument a : arguments) {
			if (a instanceof Script) {
				scripts++;
				TestUtils.outCode(source, a.getStart(), a.getEnd());
			}
		}
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(code, scripts);
		assertEquals(errs, errors.getCount());
	}
}
