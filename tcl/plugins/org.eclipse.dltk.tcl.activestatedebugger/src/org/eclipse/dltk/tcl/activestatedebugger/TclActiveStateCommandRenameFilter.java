/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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

import java.util.regex.Pattern;

import org.eclipse.dltk.dbgp.IDbgpStreamFilter;

public class TclActiveStateCommandRenameFilter implements IDbgpStreamFilter {

	private Pattern pattern;

	public TclActiveStateCommandRenameFilter() {
		try {
			pattern = Pattern
					.compile("Warning \\(issued by the debugger backend\\)\\.\nRenaming\\s+\"\\S+\" may crash the debugger\\.\n"); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			pattern = null;
		}
	}

	public String filter(String value, int stream) {
		if (pattern != null && pattern.matcher(value).matches()) {
			return null;
		} else {
			return value;
		}
	}

}
