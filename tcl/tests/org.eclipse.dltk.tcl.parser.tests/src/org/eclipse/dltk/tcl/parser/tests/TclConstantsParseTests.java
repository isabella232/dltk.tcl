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

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.Constant;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.junit.Test;

public class TclConstantsParseTests {
	public Command createConstantsCommand() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("constants");
		{
			Constant arg = factory.createConstant();
			arg.setName("alfa");
			arg.setLowerBound(1);
			arg.setUpperBound(1);
			command.getArguments().add(arg);
		}
		{
			Constant arg = factory.createConstant();
			arg.setName("beta");
			arg.setLowerBound(0);
			arg.setUpperBound(2);
			command.getArguments().add(arg);
		}
		{
			Constant arg = factory.createConstant();
			arg.setName("gamma");
			arg.setLowerBound(0);
			arg.setUpperBound(2);
			command.getArguments().add(arg);
		}
		return command;
	}

	@Test
	public void test001() {
		String source = "constants alfa gamma gamma";
		constantsCheck(source, 0);
	}

	@Test
	public void test002() {
		String source = "constants alfa beta gamma gamma";
		constantsCheck(source, 0);
	}

	@Test
	public void test003() {
		String source = "constants alfa beta gamma";
		constantsCheck(source, 0);
	}

	@Test
	public void test004() {
		String source = "constants alfa beta";
		constantsCheck(source, 0);
	}

	@Test
	public void test005() {
		String source = "constants alfa";
		constantsCheck(source, 0);
	}

	@Test
	public void test006() {
		String source = "constants alfa gamma";
		constantsCheck(source, 0);
	}

	@Test
	public void test007() {
		String source = "constants";
		constantsCheck(source, 1);
	}

	@Test
	public void test008() {
		String source = "constants alfa alfa";
		constantsCheck(source, 1);
	}

	@Test
	public void test009() {
		String source = "constants alfa alfa beta beta";
		constantsCheck(source, 1);
	}

	@Test
	public void test010() {
		String source = "constants alfa alfa beta beta beta";
		constantsCheck(source, 1);
	}

	@Test
	public void test011() {
		String source = "constants alfa alfa beta beta beta gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test012() {
		String source = "constants alfa alfa beta beta beta gamma gamma gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test013() {
		String source = "constants alfa beta beta beta gamma gamma gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test014() {
		String source = "constants [alfa] beta gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test015() {
		String source = "constants [alfa] alfa beta gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test016() {
		String source = "constants [alfa] [beta] gamma";
		constantsCheck(source, 1);
	}

	@Test
	public void test017() {
		String source = "constants beta gamma";
		constantsCheck(source, 1);
	}

	private void constantsCheck(String source, int errs) {
		TclParser parser = TestUtils.createParser();
		TestScopeProcessor manager = new TestScopeProcessor();
		TclErrorCollector errors = new TclErrorCollector();
		manager.add(createConstantsCommand());
		List<TclCommand> module = parser.parse(source, errors, manager);
		assertEquals(1, module.size());
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(errs, errors.getCount());
	}
}
