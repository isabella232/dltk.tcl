/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
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

import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IRange;
import org.eclipse.dltk.tcl.ast.TclArgument;

interface ICommand extends IRange {

	TclArgument getName();

	int getArgumentCount();

	TclArgument getArgument(int index);

	TclArgument[] getArguments();
}
