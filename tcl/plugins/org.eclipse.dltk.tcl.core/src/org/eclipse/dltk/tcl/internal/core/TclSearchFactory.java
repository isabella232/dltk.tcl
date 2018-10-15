/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core;

import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.tcl.core.TclMatchLocatorParser;
import org.eclipse.dltk.tcl.internal.core.search.TclSearchPatternProcessor;

/**
 * Tcl search factory
 */
public class TclSearchFactory extends AbstractSearchFactory {

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new TclMatchLocatorParser(locator);
	}

	public SourceIndexerRequestor createSourceRequestor() {
		return new TclSourceIndexerRequestor();
	}

	public ISearchPatternProcessor createSearchPatternProcessor() {
		return new TclSearchPatternProcessor();
	}

}
