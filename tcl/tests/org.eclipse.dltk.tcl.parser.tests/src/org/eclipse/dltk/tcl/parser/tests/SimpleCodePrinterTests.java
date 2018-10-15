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
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.printer.SimpleCodePrinter;
import org.junit.Test;

public class SimpleCodePrinterTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() {
		outCheck("after 10 {puts alpha}", "after 10 {puts alpha}");
	}

	@Test
	public void test002() {
		outCheck("source $arg/beta.tcl", "source $arg/beta.tcl");
	}

	@Test
	public void test003() {
		outCheck("source {$arg/beta 2.tcl}", "source {$arg/beta 2.tcl}");
	}

	@Test
	public void test004() {
		outCheck("source \"$arg/beta 2.tcl\"", "source \"$arg/beta 2.tcl\"");
	}

	@Test
	public void test005() {
		outCheck("source [file join $dir alfa.tcl]",
				"source [file join $dir alfa.tcl]");
	}

	@Test
	public void test006() {
		outCheck("file delete $path(gorp.file)",
				"file delete $path(gorp.file)");
	}

	@Test
	public void test007() {
		outCheck("file delete $path(gorp.file)\n",
				"file delete $path(gorp.file)");
	}

	@Test
	public void test008() {
		outCheck("file delete $path($result,$str)",
				"file delete $path($result,$str)");
	}

	@Test
	public void test009() {
		outCheck("file delete $path($result,$str)\n",
				"file delete $path($result,$str)");
	}

	@Test
	public void test010() {
		outCheck("proc hello2 {name2} {\n\t" + "puts \"Hello, $name2\"\n" + "}",
				"proc hello2 {name2} {  puts \"Hello, $name2\" }");
	}

	@Test
	public void test011() {
		outCheck("if {$DEF(cancel) == $caller} {$caller} else {.$caller}",
				"if {$DEF(cancel) == $caller} {$caller} else {.$caller}");
	}

	@Test
	public void test012() {
		String s = "if {     $DEF(cancel)      ==      $caller     } {$caller} else {.$caller}";
		outCheck(s, s);
	}

	@Test
	public void test013() {
		String s = "if      {     $DEF(         cancel             )      ==      $caller     } {      $caller    } else {.$caller}";
		outCheck(s, s);
	}

	@Test
	public void test014() {
		String s = "proc alfa {    a    {   bbbb  }  {c {    d   }   } {    }";
		outCheck(s, s);
	}

	@Test
	public void test015() {
		String s = "set a [    alfa]";
		outCheck(s, s);
	}

	@Test
	public void test016() {
		String s = "set a [    alfa      ]";
		outCheck(s, s);
	}

	@Test
	public void test017() {
		String s = "set a       [    alfa                 ]";
		outCheck(s, s);
	}

	private void outCheck(String source, String expected) {
		processor = DefinitionManager.getInstance().createProcessor();
		TclParser parser = TestUtils.createParser("8.4");
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		String actual = SimpleCodePrinter.getCommandsString(module);
		assertEquals(expected, actual);
	}
}
