/*******************************************************************************
 * Copyright (c) 2010, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.itcl.internal.core.parser.structure;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.tcl.ast.TclArgument;

class PrefixedCommandImpl implements ICommand {

	private final ICommand command;

	public PrefixedCommandImpl(ICommand command) {
		this.command = command;
		Assert.isTrue(command.getArgumentCount() >= 1);
	}

	@Override
	public TclArgument getArgument(int index) {
		return command.getArgument(index + 1);
	}

	@Override
	public int getArgumentCount() {
		return command.getArgumentCount() - 1;
	}

	@Override
	public TclArgument[] getArguments() {
		final TclArgument[] arguments = command.getArguments();
		if (arguments.length == 0) {
			throw new IllegalStateException("No more arguments in the original command");
		}
		final TclArgument[] result = new TclArgument[arguments.length - 1];
		System.arraycopy(arguments, 1, 0, 0, arguments.length - 1);
		return result;
	}

	@Override
	public TclArgument getName() {
		return command.getArgument(0);
	}

	@Override
	public int getEnd() {
		return command.getEnd();
	}

	@Override
	public int getStart() {
		return command.getArgument(0).getStart();
	}

}
