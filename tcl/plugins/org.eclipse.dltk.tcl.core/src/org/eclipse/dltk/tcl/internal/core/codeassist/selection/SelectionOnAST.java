/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core.codeassist.selection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;

public class SelectionOnAST extends SimpleReference {

	private ASTNode method;

	public SelectionOnAST(ASTNode token) {
		super(token.sourceStart(), token.sourceEnd(), "");
		this.method = token;
	}

	public ASTNode getNode() {
		return method;
	}
}
