/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.core.tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.tests.IDLTKSearchConstantsForTests;
import org.eclipse.dltk.core.tests.ProjectSetup;
import org.eclipse.dltk.core.tests.WorkspaceSetup;
import org.eclipse.dltk.core.tests.model.TestSearchResults;
import org.junit.ClassRule;
import org.junit.Test;

public class SearchVarTests implements IDLTKSearchConstants {

	public static final WorkspaceSetup WORKSPACE = new WorkspaceSetup(
			Activator.PLUGIN_ID);
	@ClassRule
	public static final ProjectSetup PROJECT = new ProjectSetup(WORKSPACE,
			"SearchVar");

	@Test
	public void testSearchVariable() throws CoreException {
		final TestSearchResults results = search("name", FIELD, REFERENCES);
		assertEquals(1, results.size());
		results.assertMethod("hello");
	}

	@Test
	public void testSearchVariableSubstitution() throws CoreException {
		final TestSearchResults results = search("name2", FIELD, REFERENCES);
		assertEquals(1, results.size());
		results.assertMethod("hello2");
	}

	public static TestSearchResults search(String patternString, int searchFor,
			int limitTo) throws CoreException {
		final IDLTKSearchScope scope = SearchEngine
				.createSearchScope(PROJECT.getScriptProject());
		int matchRule = IDLTKSearchConstantsForTests.EXACT_RULE;
		if (patternString.indexOf('*') != -1
				|| patternString.indexOf('?') != -1) {
			matchRule |= SearchPattern.R_PATTERN_MATCH;
		}
		final IDLTKLanguageToolkit toolkit = scope.getLanguageToolkit();
		final SearchPattern pattern = SearchPattern.createPattern(patternString,
				searchFor, limitTo, matchRule, toolkit);
		assertNotNull("Pattern should not be null", pattern);
		final TestSearchResults results = new TestSearchResults();
		final SearchParticipant[] participants = new SearchParticipant[] {
				SearchEngine.getDefaultSearchParticipant() };
		new SearchEngine().search(pattern, participants, scope, results, null);
		return results;
	}

}
