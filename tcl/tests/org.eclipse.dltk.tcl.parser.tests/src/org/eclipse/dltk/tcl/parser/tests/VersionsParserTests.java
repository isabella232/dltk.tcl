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
import static org.junit.Assert.assertNull;

import java.util.List;

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.Switch;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Test;

public class VersionsParserTests {
	@Test
	public void testParseVersion() {
		assertEquals(true, TclParserUtils.parseVersion("[8.1;8.2)", "8.1.1"));
		assertEquals(true,
				TclParserUtils.parseVersion("[8.1;8.2)(8.4;8.5)", "8.4.1"));
		assertEquals(false,
				TclParserUtils.parseVersion("(8.1;8.2)(8.4;8.5)", "8.1"));
		assertEquals(false,
				TclParserUtils.parseVersion("[8.1;8.2)(8.4;8.5)", "8.5"));
		assertEquals(true,
				TclParserUtils.parseVersion("[8.1;8.2)(8.4;8.5)", "8.1"));
		assertEquals(true,
				TclParserUtils.parseVersion("[8.1;8.2)(8.4;-)", "8.6"));
		assertEquals(true,
				TclParserUtils.parseVersion("[-;8.2)(8.4;8.5)", "8.0"));
		assertEquals(true,
				TclParserUtils.parseVersion("[-;-)(8.4;8.5)", "8.0"));
		assertEquals(false, TclParserUtils.parseVersion("[8.4.5;-)", "8.4.1"));
		assertEquals(true, TclParserUtils.parseVersion("[8.4.5;-)", "8.4.6"));
	}

	@Test
	public void testIsVersionValid() {
		assertEquals(true, TclParserUtils.isVersionValid("[8.1;8.2)"));
		assertEquals(true,
				TclParserUtils.isVersionValid("(8.1;8.2] \n [8.4;8.6)"));
		assertEquals(false,
				TclParserUtils.isVersionValid("(8.1;8.2] ; [8.4;8.6)"));
		assertEquals(false, TclParserUtils.isVersionValid("(8.1;8"));
		assertEquals(false, TclParserUtils.isVersionValid("(8..1;8)"));
		assertEquals(false, TclParserUtils.isVersionValid("(8.;8)"));
		assertEquals(false, TclParserUtils.isVersionValid("(8.1;8.2]]"));
		assertEquals(false, TclParserUtils.isVersionValid("(8.1);(8.2]"));
		assertEquals(false, TclParserUtils.isVersionValid("8.1"));
	}

	@Test
	public void testCompare() {
		assertEquals(-1, TclParserUtils.compareVersions("8.1", "8.2"));
		assertEquals(-1, TclParserUtils.compareVersions("8.4.1", "8.5"));
		assertEquals(0, TclParserUtils.compareVersions("8.1", "8.1"));
		assertEquals(1, TclParserUtils.compareVersions("8.2", "8.1.9"));
		assertEquals(1, TclParserUtils.compareVersions("8.1.1", "8.1"));
	}

	private static Command createNamespaceCommand(DefinitionsFactory factory,
			String version) {
		Command command = factory.createCommand();
		command.setName("namespace");
		command.setVersion(version);

		Switch kind = factory.createSwitch();
		Group evalGroup = factory.createGroup();
		evalGroup.setConstant("eval");
		// Eval group
		TypedArgument namespaceEvalName = factory.createTypedArgument();
		namespaceEvalName.setLowerBound(1);
		namespaceEvalName.setUpperBound(1);
		namespaceEvalName.setName("namespaceName");
		namespaceEvalName.setType(ArgumentType.NAMESPACE);
		evalGroup.getArguments().add(namespaceEvalName);

		TypedArgument namespaceEvalScripts = factory.createTypedArgument();
		namespaceEvalScripts.setLowerBound(1);
		namespaceEvalScripts.setUpperBound(-1);
		namespaceEvalScripts.setName("scripts");
		namespaceEvalScripts.setType(ArgumentType.SCRIPT);
		evalGroup.getArguments().add(namespaceEvalScripts);

		kind.getGroups().add(evalGroup);
		command.getArguments().add(kind);
		return command;
	}

	@Test
	public void testCommandVersions001() {
		String v1 = "(-;8.5)";
		String v2 = "[8.5;-]";
		String v = "8.4";
		String vs = v1;
		testCommandVersionsDo(v1, v2, v, vs);
	}

	@Test
	public void testCommandVersions002() {
		String v1 = "(-;8.5)";
		String v2 = "[8.5;-]";
		String v = "8.4.1";
		String vs = v1;
		testCommandVersionsDo(v1, v2, v, vs);
	}

	@Test
	public void testCommandVersions003() {
		String v1 = null;
		String v2 = "[8.5;-]";
		String v = "8.4.1";
		String vs = v1;
		testCommandVersionsDo(v1, v2, v, vs);
	}

	@Test
	public void testCommandVersions004() {
		String v1 = "(-;8.5)";
		String v2 = "[8.5;-]";
		String v = "8.5";
		String vs = v2;
		testCommandVersionsDo(v1, v2, v, vs);
	}

	@Test
	public void testCommandVersions005() {
		String v1 = "(-;8.5)";
		String v2 = "[8.5;-]";
		String v = "8.6";
		String vs = v2;
		testCommandVersionsDo(v1, v2, v, vs);
	}

	@Test
	public void testCommandVersions006() {
		String v1 = "(8.4;8.5)";
		String v = "8.4.1";
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		processor.addScope(
				createNamespaceCommand(DefinitionsFactory.eINSTANCE, v1));
		String source = "namespace eval gamma {}";
		TclParser parser = TestUtils.createParser(v);
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertNotNull(module);
		TclCommand command = module.get(0);
		assertEquals(v1, command.getDefinition().getVersion());
	}

	@Test
	public void testCommandVersions007() {
		String v1 = "(8.4;8.5)";
		String v = "8.3.1";
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		processor.addScope(
				createNamespaceCommand(DefinitionsFactory.eINSTANCE, v1));
		String source = "namespace eval gamma {}";
		TclParser parser = TestUtils.createParser(v);
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(1, errors.getCount());
		assertNotNull(module);
		TclCommand command = module.get(0);
		assertNull(command.getDefinition());
	}

	@Test
	public void testDeprecated001() {
		String v1 = "(8.4;8.5)";
		String v = "8.4.1";
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		Command cmd1 = createNamespaceCommand(DefinitionsFactory.eINSTANCE, v1);
		processor.addScope(cmd1);
		cmd1.setDeprecated("[8.4.5;-)");
		String source = "namespace eval gamma {}";
		TclParser parser = TestUtils.createParser(v);
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(0, errors.getCount());
		assertNotNull(module);
		TclCommand command = module.get(0);
		assertEquals(cmd1, command.getDefinition());
	}

	@Test
	public void testDeprecated002() {
		String v1 = "(8.4;8.5)";
		String v = "8.4.6";
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		Command cmd1 = createNamespaceCommand(DefinitionsFactory.eINSTANCE, v1);
		processor.addScope(cmd1);
		cmd1.setDeprecated("[8.4.5;-)");
		String source = "namespace eval gamma {}";
		TclParser parser = TestUtils.createParser(v);
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(1, errors.getCount());
		assertNotNull(module);
		TclCommand command = module.get(0);
		assertEquals(cmd1, command.getDefinition());
	}

	private void testCommandVersionsDo(String v1, String v2, String v,
			String vs) {
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		processor.addScope(
				createNamespaceCommand(DefinitionsFactory.eINSTANCE, v1));
		processor.addScope(
				createNamespaceCommand(DefinitionsFactory.eINSTANCE, v2));
		String source = "namespace eval gamma {}";
		TclParser parser = TestUtils.createParser(v);
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertNotNull(module);
		TclCommand command = module.get(0);
		assertEquals(vs, command.getDefinition().getVersion());
	}
}
