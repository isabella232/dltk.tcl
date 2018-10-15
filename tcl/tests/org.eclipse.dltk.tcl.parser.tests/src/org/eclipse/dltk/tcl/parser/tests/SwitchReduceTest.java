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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.Switch;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.PerformanceMonitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionUtils;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Before;
import org.junit.Test;

public class SwitchReduceTest {
	public Command createCommand001() {
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

	public Command createCommand002() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("constants");
		{
			Switch sw = factory.createSwitch();
			sw.setLowerBound(1);
			sw.setUpperBound(1);
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

	@Test
	public void testReplaceSwitch001() {
		System.out.println("TEST:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Command[] commands = DefinitionUtils.reduceSwitches(createCommand001());
		assertEquals(4, commands.length);
		for (int i = 0; i < commands.length; i++) {
			System.out.println("#" + i + ":"
					+ DefinitionUtils.convertToString(commands[i]));
		}
	}

	@Test
	public void testReplaceSwitch002() {
		System.out.println("TEST:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Command[] commands = DefinitionUtils.reduceSwitches(createCommand002());
		assertEquals(2, commands.length);
		for (int i = 0; i < commands.length; i++) {
			System.out.println("#" + i + ":"
					+ DefinitionUtils.convertToString(commands[i]));
		}
	}

	NamespaceScopeProcessor processor;

	@Before
	public void setUp() {
		processor = DefinitionManager.getInstance().createProcessor();
	}

	@Test
	public void testReplaceSwitch004() {
		System.out.println("TEST:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Command commands[] = processor.getCommandDefinition("open");
		PerformanceMonitor.getDefault().begin("Command reduction");
		for (int i = 0; i < commands.length; i++) {
			System.out.println("#ORIGINAL:" + i + ":"
					+ DefinitionUtils.convertToString(commands[i]));
			Map<String, Object> options = new HashMap<>();
			options.put(DefinitionUtils.GENERATE_VARIANTS, true);
			Command[] rc = DefinitionUtils.reduceSwitches(commands[i], options);
			if (rc.length > 1) {
				for (int j = 0; j < rc.length; j++) {
					System.out.println(
							DefinitionUtils.convertToString(rc[j], true));
				}
			}
		}
		PerformanceMonitor.getDefault().end("Command reduction");
		PerformanceMonitor.getDefault().print();
	}
}
