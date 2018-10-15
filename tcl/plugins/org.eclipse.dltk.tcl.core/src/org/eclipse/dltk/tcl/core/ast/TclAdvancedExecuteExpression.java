/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.core.ast;

import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.tcl.ast.TclConstants;
import org.eclipse.dltk.utils.CorePrinter;

public class TclAdvancedExecuteExpression extends Block {
	public TclAdvancedExecuteExpression(int start, int end) {
		this.setStart(start);
		this.setEnd(end);
	}

	public int getKind() {
		return TclConstants.TCL_EXECUTE_EXPRESSION;
	}
	
	public void printNode(CorePrinter output) {
		output.formatPrintLn("Expression" + getSourceRange() + ":" + getKind()); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
