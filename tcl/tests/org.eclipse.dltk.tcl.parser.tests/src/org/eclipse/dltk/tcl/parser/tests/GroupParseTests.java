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
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class GroupParseTests {
	public Command createConstantsCommand() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("group");
		{
			Group group = factory.createGroup();
			group.setConstant(null);
			group.setLowerBound(0);
			group.setUpperBound(-1);
			{
				Constant arg = factory.createConstant();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("const");
				group.getArguments().add(arg);
			}
			{
				TypedArgument arg = factory.createTypedArgument();
				arg.setType(ArgumentType.ANY);
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("varName");
				group.getArguments().add(arg);
			}
			{
				TypedArgument arg = factory.createTypedArgument();
				arg.setType(ArgumentType.ANY);
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("varName");
				group.getArguments().add(arg);
			}
			command.getArguments().add(group);
		}
		return command;
	}

	@Test
	public void test001() {
		String source = "group";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test002() {
		String source = "group const val";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test003() {
		String source = "group const val val";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test004() {
		String source = "group val val";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test005() {
		String source = "group const val val val";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test006() {
		String source = "group const val val const val val";
		typedCheck(source, 0, 0);
	}

	private void typedCheck(String source, int errs, int code) {
		TclParser parser = TestUtils.createParser();
		TestScopeProcessor manager = new TestScopeProcessor();
		TclErrorCollector errors = new TclErrorCollector();
		manager.add(createConstantsCommand());
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
