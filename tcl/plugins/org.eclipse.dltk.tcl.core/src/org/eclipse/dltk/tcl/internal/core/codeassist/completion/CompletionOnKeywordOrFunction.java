/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core.codeassist.completion;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.codeassist.complete.ICompletionOnKeyword;
import org.eclipse.dltk.utils.CorePrinter;

public class CompletionOnKeywordOrFunction extends SimpleReference implements
		ICompletionOnKeyword {

	private String[] possibleKeywords;
	private ASTNode parent;

	public CompletionOnKeywordOrFunction(String token, ASTNode completionNode,
			ASTNode node, String[] possibleKeywords) {
		super(completionNode.sourceStart(), completionNode.sourceEnd(), token);
		this.possibleKeywords = possibleKeywords;
		this.parent = node;
	}

	public char[] getToken() {
		String name = getName();
		if (name == null) {
			return new char[0];
		}
		return name.toCharArray();
	}

	public String[] getPossibleKeywords() {
		return this.possibleKeywords;
	}

	public void printNode(CorePrinter output) {
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
	}

	public boolean canCompleteEmptyToken() {
		return true;
	}

	public ASTNode getInParent() {
		return this.parent;
	}
}
