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
package org.eclipse.dltk.tcl.parser.definitions;

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.parser.ISubstitutionManager;

public interface IScopeProcessor {
	Command[] getCommandDefinition(String command);

	void processCommand(TclCommand command);

	void endProcessCommand();

	String getQualifiedName(String commandValue);

	ISubstitutionManager getSubstitutionManager();

	boolean checkCommandScope(Command command);
}
