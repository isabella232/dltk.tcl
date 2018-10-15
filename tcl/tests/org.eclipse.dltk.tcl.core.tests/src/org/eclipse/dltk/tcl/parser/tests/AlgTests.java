/*******************************************************************************
 * Copyright (c) 2005, 207 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.tests;

import org.eclipse.dltk.tcl.core.TclParseUtil;
import org.junit.Test;

import junit.framework.TestCase;

public class AlgTests {
	@Test
	public void test001() {
		String s = "";
		check(s);
	}

	@Test
	public void test002() {
		String s = "a:b";
		check(s);
	}

	@Test
	public void test003() {
		String s = "a::b";
		check(s);
	}

	@Test
	public void test004() {
		String s = "::a:b";
		check(s);
	}

	@Test
	public void test005() {
		String s = "a:b";
		check(s);
	}

	@Test
	public void test006() {
		String s = "a:b::";
		check(s);
	}

	@Test
	public void test007() {
		String s = "a::b::c";
		check(s);
	}

	@Test
	public void test008() {
		String s = "a:::b";
		check(s);
	}

	@Test
	public void test009() {
		String s = "a::b:::";
		check(s);
	}

	@Test
	public void test00() {
		String s = ":::a::b";
		check(s);
	}

	private void check(String s) {
		String[] tclSplit = TclParseUtil.tclSplit(s);
		String[] split = s.split("::");
		TestCase.assertEquals(split.length, tclSplit.length);
		for (int i = 0; i < split.length; i++) {
			TestCase.assertEquals(split[i], tclSplit[i]);
		}
	}
}
