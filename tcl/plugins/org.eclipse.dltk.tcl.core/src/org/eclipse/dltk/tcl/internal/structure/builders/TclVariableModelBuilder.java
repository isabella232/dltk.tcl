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
package org.eclipse.dltk.tcl.internal.structure.builders;

import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.structure.AbstractTclCommandModelBuilder;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;

public class TclVariableModelBuilder extends AbstractTclCommandModelBuilder {

	public boolean process(TclCommand command, ITclModelBuildContext context) {
		if (command.getArguments().size() < 1) {
			report(context, command,
					"Syntax error: at least one argument expected.",
					ProblemSeverities.Error);
			return false;
		}
		final TclArgument nameArg = command.getArguments().get(0);
		processField(command, nameArg, context);
		return false;
	}

}
