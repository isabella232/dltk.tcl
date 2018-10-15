/*******************************************************************************
 * Copyright (c) 2010, 2017 xored software, Inc. and others.
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

import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.emf.common.util.EList;

class CommandImpl implements ICommand {

	private final TclCommand command;

	public CommandImpl(TclCommand cmd) {
		this.command = cmd;
	}

	@Override
	public TclArgument getName() {
		return command.getName();
	}

	@Override
	public int getEnd() {
		return command.getEnd();
	}

	@Override
	public int getStart() {
		return command.getStart();
	}

	@Override
	public TclArgument getArgument(int index) {
		return command.getArguments().get(index);
	}

	@Override
	public int getArgumentCount() {
		return command.getArguments().size();
	}

	@Override
	public TclArgument[] getArguments() {
		final EList<TclArgument> args = command.getArguments();
		return args.toArray(new TclArgument[args.size()]);
	}

}
