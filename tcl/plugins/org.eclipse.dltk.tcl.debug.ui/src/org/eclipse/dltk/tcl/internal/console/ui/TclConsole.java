/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui;

import org.eclipse.dltk.console.ui.ScriptConsole;
import org.eclipse.dltk.tcl.console.TclInterpreter;
import org.eclipse.dltk.tcl.internal.debug.ui.TclDebugUIPlugin;
import org.eclipse.osgi.util.NLS;

public class TclConsole extends ScriptConsole {
	public static final String CONSOLE_TYPE = "tcl_console"; //$NON-NLS-1$

	public TclConsole(TclInterpreter interpreter, String id) {
		super(NLS.bind(Messages.TclConsole_Name, id), CONSOLE_TYPE,
				TclDebugUIPlugin.imageDescriptorFromPlugin(
						TclDebugUIPlugin.PLUGIN_ID,
						"icons/obj16/console_obj.gif")); //$NON-NLS-1$

		setInterpreter(interpreter);
		setTextHover(new TclConsoleTextHover(interpreter));
		setContentAssistProcessor(new TclConsoleCompletionProcessor(interpreter));
	}
}
