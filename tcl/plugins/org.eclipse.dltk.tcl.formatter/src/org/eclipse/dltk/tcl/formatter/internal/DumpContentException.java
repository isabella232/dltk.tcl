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
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/

package org.eclipse.dltk.tcl.formatter.internal;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This exception class is used to dump large content to the stack trace field
 */
public class DumpContentException extends Exception {

	private static final long serialVersionUID = 7338104720710784097L;

	public DumpContentException(String message) {
		super(message);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			s.println("========"); //$NON-NLS-1$
			s.println(getMessage());
			s.println("========"); //$NON-NLS-1$
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			s.println("========"); //$NON-NLS-1$
			s.println(getMessage());
			s.println("========"); //$NON-NLS-1$
		}
	}

}
