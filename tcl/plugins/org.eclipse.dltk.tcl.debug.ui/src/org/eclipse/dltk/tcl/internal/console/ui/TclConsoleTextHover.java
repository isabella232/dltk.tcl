/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui;

import java.io.IOException;

import org.eclipse.dltk.console.IScriptConsoleShell;
import org.eclipse.dltk.console.ui.IScriptConsoleViewer;
import org.eclipse.dltk.console.ui.ScriptConsoleTextHover;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.jface.text.IRegion;

public class TclConsoleTextHover extends ScriptConsoleTextHover {

	private IScriptConsoleShell interpreterShell;

	public TclConsoleTextHover(IScriptConsoleShell interpreterShell) {
		this.interpreterShell = interpreterShell;
	}

	@Override
	protected String getHoverInfoImpl(IScriptConsoleViewer viewer,
			IRegion hoverRegion) {
		try {
			int cursorPosition = hoverRegion.getOffset()
					- viewer.getCommandLineOffset();

			String commandLine = viewer.getCommandLine();
			if (commandLine == null) {
				return null;
			}

			return interpreterShell.getDescription(commandLine, cursorPosition);
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
