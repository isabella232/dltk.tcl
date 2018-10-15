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
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.internal.core.parser.processors.tcl.Messages;
import org.eclipse.dltk.tcl.structure.AbstractTclCommandModelBuilder;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;

public class TclCatchModelBuilder extends AbstractTclCommandModelBuilder {

	public boolean process(TclCommand command, ITclModelBuildContext context) {
		if (command.getArguments().isEmpty()
				|| command.getArguments().size() > 2) {
			report(context, command,
					Messages.TclProcProcessor_Wrong_Number_of_Arguments,
					ProblemSeverities.Error);
			return false;
		}
		if (command.getArguments().size() == 2) {
			processField(command, command.getArguments().get(1), context);
		}
		return true;
	}

}
