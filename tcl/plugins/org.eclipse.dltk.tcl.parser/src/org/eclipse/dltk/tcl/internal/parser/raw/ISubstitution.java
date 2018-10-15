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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser.raw;

public interface ISubstitution {
	
	
	/**
	 * Reads substitution and initializes object from it. 
	 * @param scanner
	 * @return false if subs. doesn't fit
	 */
	public boolean readMe (ICodeScanner scanner, SimpleTclParser parser) throws TclParseException;
	
}
