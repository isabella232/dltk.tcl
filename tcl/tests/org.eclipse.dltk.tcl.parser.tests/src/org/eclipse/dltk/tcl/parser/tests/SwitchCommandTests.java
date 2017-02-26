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
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Test;

public class SwitchCommandTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() throws Exception {
		String source = "switch $some {a {puts $some}}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test002() throws Exception {
		String source = "switch -glob -- -some {a concat2}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test003() throws Exception {
		String source = "switch -glob -- $some {a \"puts $some\"}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test004() throws Exception {
		String source = "switch -glob -- $some {a {puts $some} b \"puts $some\" c history}";
		typedCheck(source, 0, 3);
	}

	@Test
	public void test006() throws Exception {
		String source = "switch -exact -- $caller {cancel {puts cancel}}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test007() throws Exception {
		String source = "switch $caller {cancel return cancel2 return}";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test008() throws Exception {
		String source = "switch -- $caller {cancel return cancel2 return}";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test009() throws Exception {
		String source = "switch -- $caller cancel return cancel2 return2";
		typedCheck(source, 0, 2);
	}

	@Test
	public void test010() throws Exception {
		// cancel - unknown command - ats_rmserver
		String source = "switch $caller {{cancel} {puts cancel}}";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test011() throws Exception {
		// -jobs unknown command - autoeasy_abort
		String source = "switch -exact -- $i { -jobs { set flag 1} }";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test012() throws Exception {
		// -jobs unknown command - autoeasy_abort
		String source = "switch -exact -- $i { \"jobs\" { set flag 1} }";
		typedCheck(source, 0, 1);
	}

	@Test
	public void test013() throws Exception {
		String script = "switch -exact -regexp -glob \"\" {"
				+ "	[func] {puts py!}" + "	default {puts boo}" + " }";
		typedCheck(script, 0, 2);
	}

	@Test
	public void test014() throws Exception {
		String script = "switch string {" + "set {" + "	pid" + "} "
				+ "default {puts boo}}";
		typedCheck(script, 0, 2);
	}

	private void typedCheck(final String source, int errs, int code)
			throws Exception {
		System.out.println("=============================================");
		processor = DefinitionManager.getInstance().createProcessor();
		TclParser parser = TestUtils.createParser("8.4");
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		assertEquals(1, module.size());
		final int[] scripts = { 0 };
		TclParserUtils.traverse(module, new TclVisitor() {
			@Override
			public boolean visit(Script script) {
				scripts[0]++;
				TestUtils.outCode(source, script.getStart(), script.getEnd());
				return true;
			}
		});
		assertEquals(code, scripts[0]);

		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(errs, errors.getCount());
	}
}
