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

import java.util.List;

import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Test;

import junit.framework.TestCase;

public class SocketCommandTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() {
		String source = "socket $host $port";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test002() {
		String source = "socket -server [list ::ftpd::PasvAccept $sock] 0";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test003() {
		String source = "set data(sock2a) [socket -server [list ::ftpd::PasvAccept $sock] 0]";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test004() {
		String source = "socket -server lala";
		typedCheck(source, 1, 0);
	}

	private void typedCheck(String source, int errs, int code) {
		processor = DefinitionManager.getInstance().createProcessor();
		TclParser parser = TestUtils.createParser();
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		TestCase.assertEquals(1, module.size());
		// TclCommand tclCommand = module.get(0);
		// EList<TclArgument> arguments = tclCommand.getArguments();
		final int[] scripts = { 0 };
		TclParserUtils.traverse(module, new TclVisitor() {
			@Override
			public boolean visit(Script script) {
				scripts[0]++;
				return true;
			}
		});
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		TestCase.assertEquals(code, scripts[0]);
		TestCase.assertEquals(errs, errors.getCount());
	}
}
