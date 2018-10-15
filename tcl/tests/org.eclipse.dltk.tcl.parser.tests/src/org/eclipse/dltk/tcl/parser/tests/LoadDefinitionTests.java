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

import java.net.URL;

import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.Scope;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionLoader;
import org.eclipse.dltk.tcl.parser.internal.tests.Activator;
import org.junit.Test;

public class LoadDefinitionTests {
	@Test
	public void testLoad001() throws Exception {
		TestScopeProcessor processor = new TestScopeProcessor();
		Scope scope = DefinitionLoader.loadDefinitions(new URL(
				"platform:///plugin/org.eclipse.dltk.tcl.parser.tests/definitions/test0.xml"));
		assertNotNull(scope);
		processor.add(scope);
		Command[] setCommand = processor.getCommandDefinition("set");
		assertNotNull(setCommand[0]);
		assertEquals("set", setCommand[0].getName());
		Command[] unsetCommand = processor.getCommandDefinition("unset");
		assertNotNull(unsetCommand[0]);
		assertEquals("unset", unsetCommand[0].getName());
	}

	@Test
	public void testLoad002() throws Exception {
		TestScopeProcessor processor = new TestScopeProcessor();
		Scope scope = DefinitionLoader.loadDefinitions(Activator.getDefault()
				.getBundle().getEntry("/definitions/test0.xml"));
		assertNotNull(scope);
		processor.add(scope);
		Command[] setCommand = processor.getCommandDefinition("set");
		assertNotNull(setCommand);
		assertEquals("set", setCommand[0].getName());
		Command[] unsetCommand = processor.getCommandDefinition("unset");
		assertNotNull(unsetCommand);
		assertEquals("unset", unsetCommand[0].getName());
	}
}
