/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.ast;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

public class TclStatement extends Statement {
	private List<ASTNode> expressions;

	public TclStatement(List<ASTNode> expressions) {
		if (!expressions.isEmpty()) {
			// First
			Expression first = (Expression) expressions.get(0);
			this.setStart(first.sourceStart());

			// Last
			Expression last = (Expression) expressions
					.get(expressions.size() - 1);
			this.setEnd(last.sourceEnd());
		}

		this.expressions = expressions;
	}

	public List<ASTNode> getExpressions() {
		return this.expressions;
	}

	public Expression getAt(int index) {
		if (index >= 0 && index < this.expressions.size()) {
			return (Expression) this.expressions.get(index);
		}

		return null;
	}

	public int getCount() {
		return this.expressions.size();
	}

	@Override
	public int getKind() {
		return TclConstants.TCL_STATEMENT;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (this.expressions != null) {
				int exprSize = this.expressions.size();
				for (int i = 0; i < exprSize; i++) {
					ASTNode node = this.expressions.get(i);
					node.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	@Override
	public void printNode(CorePrinter output) {
		if (this.expressions != null) {
			output.formatPrintLn("");
			Iterator<ASTNode> i = this.expressions.iterator();
			while (i.hasNext()) {
				ASTNode node = i.next();
				node.printNode(output);
				output.formatPrintLn(" ");
			}
		}
	}

	@Override
	public String toString() {
		String value = "";
		if (this.expressions != null) {
			Iterator<ASTNode> i = this.expressions.iterator();
			while (i.hasNext()) {
				ASTNode node = i.next();
				value += node.toString();
				value += " ";
			}
		}

		return value;
	}

	public void setExpressions(List<ASTNode> asList) {
		this.expressions = asList;
	}
}
