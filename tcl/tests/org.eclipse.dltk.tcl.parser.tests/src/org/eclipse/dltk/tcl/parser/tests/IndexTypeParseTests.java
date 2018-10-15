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
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class IndexTypeParseTests {
	public Command createConstantsCommand() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("cmd");
		{
			TypedArgument arg = factory.createTypedArgument();
			arg.setType(ArgumentType.INDEX);
			arg.setName("index");
			command.getArguments().add(arg);
		}

		return command;
	}

	@Test
	public void test001() {
		String source = "cmd 34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test002() {
		String source = "cmd end";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test003() {
		String source = "cmd end-34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test004() {
		String source = "cmd end+34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test005() {
		String source = "cmd 21-34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test006() {
		String source = "cmd 56-34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test007() {
		String source = "cmd -34";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test008() {
		String source = "cmd end--34";
		typedCheck(source, 1, 0);
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
			System.out.println("=============================================");
		}
		assertEquals(code, scripts);
		assertEquals(errs, errors.getCount());
	}
}
