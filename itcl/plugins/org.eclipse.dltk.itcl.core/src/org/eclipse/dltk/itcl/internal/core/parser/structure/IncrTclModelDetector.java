/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.itcl.internal.core.parser.structure;

import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IClass;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;
import org.eclipse.dltk.tcl.structure.ITclModelBuilderDetector;
import org.eclipse.dltk.tcl.structure.TclModelBuilderUtil;

public class IncrTclModelDetector extends TclModelBuilderUtil implements ITclModelBuilderDetector {

	private static final String[] NAMESPACES = new String[] { "::itcl::", "itcl::" };

	private static final String[] COMMANDS = new String[] { "class", "body" };

	// ,
	// "code", "configbody", "delete", "ensemble", "find", "local",
	// "scope"

	private static final String PREFIX = "#itcl#"; //$NON-NLS-1$
	private static final String ITCL_NEW_INSTANCE = PREFIX + "$newInstance"; //$NON-NLS-1$

	/**
	 * Remove iTcl namespaces from the specified command name
	 *
	 * @param commandName
	 * @return command name without namespace
	 */
	private String normalize(String commandName) {
		for (String namespace : NAMESPACES) {
			if (commandName.startsWith(namespace)) {
				return commandName.substring(namespace.length());
			}
		}
		return commandName;
	}

	@Override
	public String detect(String commandName, TclCommand command, ITclModelBuildContext context) {
		if (commandName == null) {
			return null;
		}
		commandName = normalize(commandName);
		for (String cmd : COMMANDS) {
			if (commandName.equals(cmd)) {
				return PREFIX + cmd;
			}
		}
		return checkInstanceOperations(command, commandName, context);
	}

	/**
	 * @param command
	 * @param commandName
	 * @param context
	 * @return
	 */
	private String checkInstanceOperations(TclCommand command, String commandName, ITclModelBuildContext context) {
		final IncrTclNames names = IncrTclNames.get(context);
		if (names != null) {
			final IClass type = names.resolve(commandName);
			if (type != null) {
				IncrTclNames.saveType(context, type);
				return ITCL_NEW_INSTANCE;
			}
		}
		// TODO search global class list
		return null;
	}

}
