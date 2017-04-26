/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.Constant;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.Switch;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class TclSwitchArgumentsParseTests {
	public Command createGroupCommand001() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("constants");
		{
			Switch sw = factory.createSwitch();
			sw.setLowerBound(1);
			sw.setUpperBound(2);
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-id");

				{
					TypedArgument arg2 = factory.createTypedArgument();
					arg2.setType(ArgumentType.SCRIPT);
					arg2.setName("beta");
					arg2.setLowerBound(1);
					arg2.setUpperBound(1);
					arg.getArguments().add(arg2);
				}
				sw.getGroups().add(arg);
			}
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-value");
				{
					TypedArgument arg2 = factory.createTypedArgument();
					arg2.setType(ArgumentType.SCRIPT);
					arg2.setName("gamma");
					arg2.setLowerBound(1);
					arg2.setUpperBound(1);
					arg.getArguments().add(arg2);
				}
				sw.getGroups().add(arg);
			}
			command.getArguments().add(sw);
		}
		return command;
	}

	public Command createGroupCommand002() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("constants");
		{
			Switch sw = factory.createSwitch();
			sw.setLowerBound(0);
			sw.setUpperBound(2);
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-id");

				{
					TypedArgument arg2 = factory.createTypedArgument();
					arg2.setType(ArgumentType.SCRIPT);
					arg2.setName("beta");
					arg2.setLowerBound(1);
					arg2.setUpperBound(1);
					arg.getArguments().add(arg2);
				}
				sw.getGroups().add(arg);
			}
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-value");
				{
					TypedArgument arg2 = factory.createTypedArgument();
					arg2.setType(ArgumentType.SCRIPT);
					arg2.setName("gamma");
					arg2.setLowerBound(1);
					arg2.setUpperBound(1);
					arg.getArguments().add(arg2);
				}
				sw.getGroups().add(arg);
			}
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-delta");
				sw.getGroups().add(arg);
			}
			{
				Group arg = factory.createGroup();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setConstant("-teta");
				sw.getGroups().add(arg);
			}
			command.getArguments().add(sw);
			{
				Constant a = factory.createConstant();
				a.setName("--");
				a.setLowerBound(1);
				a.setUpperBound(1);
				command.getArguments().add(a);
			}
			{
				TypedArgument arg2 = factory.createTypedArgument();
				arg2.setType(ArgumentType.SCRIPT);
				arg2.setName("code");
				arg2.setLowerBound(1);
				arg2.setUpperBound(1);
				command.getArguments().add(arg2);
			}
		}
		return command;
	}

	@Test
	public void test001() {
		String source = "constants -id {set a 20}";
		check001(source, 0, 1);
	}

	@Test
	public void test002() {
		String source = "constants -id {set a 20} -id {set a 20}";
		check001(source, 0, 2);
	}

	@Test
	public void test003() {
		String source = "constants -id {set a 20} -value {set a 20}";
		check001(source, 0, 2);
	}

	@Test
	public void test004() {
		String source = "constants -value {set a 20} -value {set a 20}";
		check001(source, 0, 2);
	}

	@Test
	public void test005() {
		String source = "constants -value {set a 20} -id {set a 20}";
		check001(source, 0, 2);
	}

	@Test
	public void test006() {
		String source = "constants -value {set a 20}";
		check001(source, 0, 1);
	}

	@Test
	public void test007() {
		String source = "constants";
		check001(source, 1, 0);
	}

	@Test
	public void test008() {
		String source = "constants -- {set a 20}";
		check002(source, 0, 1);
	}

	@Test
	public void test009() {
		String source = "constants -delta -- {set a 20}";
		check002(source, 0, 1);
	}

	@Test
	public void test010() {
		String source = "constants -delta -teta -- {set a 20}";
		check002(source, 0, 1);
	}

	@Test
	public void test011() {
		String source = "constants -id {set a 30} -teta -- {set a 20}";
		check002(source, 0, 2);
	}

	@Test
	public void test012() {
		String source = "constants -teta -value {set a 30} -- {set a 20}";
		check002(source, 0, 2);
	}

	@Test
	public void test013() {
		String source = "constants -teta -- {set a 20}";
		check002(source, 0, 1);
	}

	private void check001(String source, int errs, int code) {
		TestScopeProcessor manager = new TestScopeProcessor();
		manager.add(createGroupCommand001());
		check(source, errs, code, manager);
	}

	private void check002(String source, int errs, int code) {
		TestScopeProcessor manager = new TestScopeProcessor();
		manager.add(createGroupCommand002());
		check(source, errs, code, manager);
	}

	private void check(String source, int errs, int code,
			TestScopeProcessor manager) {
		System.out.println("TEST:"
				+ Thread.currentThread().getStackTrace()[3].getMethodName());
		TclParser parser = TestUtils.createParser();
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, manager);
		assertEquals(1, module.size());
		TclCommand tclCommand = module.get(0);
		EList<TclArgument> arguments = tclCommand.getArguments();
		int scripts = 0;
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i) instanceof Script) {
				scripts++;
			}
		}
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(code, scripts);
		assertEquals(errs, errors.getCount());
	}
}
