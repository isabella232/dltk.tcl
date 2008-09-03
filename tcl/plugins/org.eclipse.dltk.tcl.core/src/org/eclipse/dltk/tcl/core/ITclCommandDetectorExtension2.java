package org.eclipse.dltk.tcl.core;

import org.eclipse.dltk.ast.ASTNode;

public interface ITclCommandDetectorExtension2 {
	/**
	 * Called after processing of each node.
	 */
	void processASTNode(ASTNode node);
}
