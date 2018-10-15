/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.ast.expressions;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclConstants;
import org.eclipse.dltk.tcl.core.ITclSourceParser;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.utils.CorePrinter;

public class TclBlockExpression extends Expression {
	private String fBlockContent;
	private String fileName = null;
	private TclArgument processedArgument;

	public TclBlockExpression(int start, int end, String content) {
		this.setStart(start);
		this.setEnd(end);
		this.fBlockContent = content;
	}

	public void setProcessedArgument(TclArgument processedArgument) {
		this.processedArgument = processedArgument;
	}

	public TclArgument getProcessedArgument() {
		return processedArgument;
	}

	@Override
	public int getKind() {
		return TclConstants.TCL_BLOCK_EXPRESSION;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			visitor.endvisit(this);
		}
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn("tcl block:" + this.fBlockContent);
	}

	@Override
	public String toString() {
		return "tcl block:" + this.fBlockContent;
	}

	public String getBlock() {
		return this.fBlockContent;
	}

	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	public List parseBlockSimple() {
		return parseBlockSimple(true);
	}

	public List parseBlockSimple(boolean useProcessors) {
		if (this.fBlockContent == null) {
			return null;
		}

		String content = this.fBlockContent.substring(1,
				this.fBlockContent.length() - 1);
		ITclSourceParser parser = null;
		parser = (ITclSourceParser) DLTKLanguageManager
				.getSourceParser(TclNature.NATURE_ID);
		parser.setProcessorsState(useProcessors);
		parser.setOffset(this.sourceStart() + 1);
		ModuleDeclaration module = parser
				.parse(new ModuleSource(this.fileName, content), null);
		return module.getStatements();
	}
}
