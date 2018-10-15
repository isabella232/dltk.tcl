/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.core.tests;

import org.eclipse.dltk.tcl.core.tests.model.PACompletionTests;
import org.eclipse.dltk.tcl.core.tests.model.PASelectionTests;
import org.eclipse.dltk.tcl.parser.structure.SimpleStructureParserTests;
import org.eclipse.dltk.tcl.parser.structure.StructureParserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ org.eclipse.dltk.tcl.core.tests.model.AllTests.class,
		org.eclipse.dltk.tcl.parser.tests.AllTests.class,
		PACompletionTests.class, PASelectionTests.class,
		VariableResolverTests.class, TclContentDescriberTests.class,
		SimpleStructureParserTests.class, StructureParserTests.class })
public class AllTests {
}
