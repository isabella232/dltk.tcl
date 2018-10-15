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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class TclParserTests {
	@Test
	public void testParser001() {
		TclParser parser = TestUtils.createParser();
		String source = "set a 20\n" + "set c 30";
		List<TclCommand> module = parser.parse(source);
		assertEquals(2, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertEquals(2, st1.getArguments().size());
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("set", ref1.getValue());

		TclArgument exp2 = st1.getArguments().get(0);
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref2 = (StringArgument) exp2;
		assertEquals("a", ref2.getValue());
		TclArgument exp3 = st1.getArguments().get(1);
		assertTrue(exp3 instanceof StringArgument);
		StringArgument ref3 = (StringArgument) exp3;
		assertEquals("20", ref3.getValue());
	}

	@Test
	public void testParser002() {
		TclParser parser = TestUtils.createParser();
		String source = "set a \"wer\"\n" + "set c 30";
		List<TclCommand> module = parser.parse(source);
		assertNotNull(module);
		assertEquals(2, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("set", ref1.getValue());

		assertEquals(2, st1.getArguments().size());
		TclArgument exp2 = st1.getArguments().get(0);
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref2 = (StringArgument) exp2;
		assertEquals("a", ref2.getValue());
		TclArgument exp3 = st1.getArguments().get(1);
		assertTrue(exp3 instanceof StringArgument);
		StringArgument ref3 = (StringArgument) exp3;
		assertEquals("\"wer\"", ref3.getValue());
	}

	@Test
	public void testParser003() {
		TclParser parser = TestUtils.createParser();
		String source = "set a {wer}\n" + "set c 30";
		List<TclCommand> module = parser.parse(source);
		assertNotNull(module);
		assertEquals(2, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("set", ref1.getValue());

		assertEquals(2, st1.getArguments().size());
		TclArgument exp2 = st1.getArguments().get(0);
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref2 = (StringArgument) exp2;
		assertEquals("a", ref2.getValue());
		TclArgument exp3 = st1.getArguments().get(1);
		assertTrue(exp3 instanceof StringArgument);
		StringArgument ref3 = (StringArgument) exp3;
		assertEquals("{wer}", ref3.getValue());
	}

	@Test
	public void testParser004() {
		TclParser parser = TestUtils.createParser();
		String source = "set a [wer]\n" + "set c 30";
		List<TclCommand> module = parser.parse(source);
		assertNotNull(module);
		assertEquals(2, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("set", ref1.getValue());

		assertEquals(2, st1.getArguments().size());
		TclArgument exp2 = st1.getArguments().get(0);
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref2 = (StringArgument) exp2;
		assertEquals("a", ref2.getValue());

		TclArgument exp3 = st1.getArguments().get(1);
		assertTrue(exp3 instanceof Substitution);
		Substitution subst = (Substitution) exp3;
		EList<TclCommand> list = subst.getCommands();
		assertEquals(1, list.size());
		TclCommand anyCommand = list.get(0);
		assertNotNull(anyCommand);
		EList<TclArgument> arguments = anyCommand.getArguments();
		assertEquals(0, arguments.size());
		TclArgument argument = anyCommand.getName();
		assertTrue(argument instanceof StringArgument);
		assertEquals("wer", ((StringArgument) argument).getValue());
	}

	@Test
	public void testParser005() {
		TclParser parser = TestUtils.createParser();
		String source = "puts \"alfa[de]be$teta\" $delta";
		List<TclCommand> module = parser.parse(source);
		assertEquals(1, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertEquals(2, st1.getArguments().size());
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("puts", ref1.getValue());
	}

	@Test
	public void testParser006() {
		TclParser parser = TestUtils.createParser();
		String source = "incr i -$length";
		List<TclCommand> module = parser.parse(source);
		assertEquals(1, module.size());

		TclCommand st1 = module.get(0);
		TclArgument exp1 = st1.getName();
		assertEquals(2, st1.getArguments().size());
		assertTrue(exp1 instanceof StringArgument);
		StringArgument ref1 = (StringArgument) exp1;
		assertEquals("incr", ref1.getValue());
	}

	@Test
	public void testSimpleErrors001() {
		TclParser parser = TestUtils.createParser();
		String source = "set a \"This is\nset b 20";
		TclErrorCollector collector = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, collector, null);
		assertEquals(1, module.size());
		TestUtils.outErrors(source, collector);
		assertEquals(1, collector.getCount());
	}

	@Test
	public void testSimpleErrors002() {
		TclParser parser = TestUtils.createParser();
		String source = "set a [This is\nset b 20";
		TclErrorCollector collector = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, collector, null);
		assertEquals(1, module.size());
		TestUtils.outErrors(source, collector);
		assertEquals(1, collector.getCount());
	}

	@Test
	public void testSimpleErrors003() {
		TclParser parser = TestUtils.createParser();
		String source = "set a {This is\nset b 20";
		TclErrorCollector collector = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, collector, null);
		assertEquals(1, module.size());
		TestUtils.outErrors(source, collector);
		assertEquals(1, collector.getCount());
	}
}
