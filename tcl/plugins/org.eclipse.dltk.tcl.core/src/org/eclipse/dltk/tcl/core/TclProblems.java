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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.core;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;

public enum TclProblems implements IProblemIdentifier {

	UNKNOWN_REQUIRED_PACKAGE, UNKNOWN_REQUIRED_PACKAGE_CORRECTION, UNKNOWN_SOURCE, UNKNOWN_SOURCE_CORRECTION;

	public String contributor() {
		return TclPlugin.PLUGIN_ID;
	}

}
