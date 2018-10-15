/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *     xored software, Inc. - todo task parser added (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.tcl.core.TclNature;

public class TclSourceElementParser extends AbstractSourceElementParser {

	/*
	 * @see org.eclipse.dltk.core.AbstractSourceElementParser#createVisitor()
	 */
	protected SourceElementRequestVisitor createVisitor() {
		return new TclSourceElementRequestVisitor(this.getRequestor(), this
				.getProblemReporter());
	}

	/*
	 * @see org.eclipse.dltk.core.AbstractSourceElementParser#getNatureId()
	 */
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

}
