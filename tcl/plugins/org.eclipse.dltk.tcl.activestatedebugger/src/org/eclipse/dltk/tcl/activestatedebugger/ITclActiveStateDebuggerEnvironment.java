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
package org.eclipse.dltk.tcl.activestatedebugger;

public interface ITclActiveStateDebuggerEnvironment {

	/**
	 * Return the debugger engine path configured for this environment or
	 * <code>null</code>.
	 */
	public String getDebuggerPath();

	/**
	 * Sets the debugger engine path for this environment
	 * 
	 * @param path
	 */
	public void setDebuggerPath(String path);

	public String getPDXPath();

	public void setPDXPath(String path);

	public boolean isLoggingEnabled();

	public void setLoggingEnabled(boolean value);

	public String getLoggingPath();

	public void setLoggingPath(String path);

}
