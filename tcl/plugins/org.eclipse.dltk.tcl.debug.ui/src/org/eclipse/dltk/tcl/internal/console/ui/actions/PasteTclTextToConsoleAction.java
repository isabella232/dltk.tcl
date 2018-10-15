/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui.actions;

import org.eclipse.dltk.console.ui.IScriptConsole;
import org.eclipse.dltk.console.ui.ScriptConsoleManager;
import org.eclipse.dltk.tcl.internal.console.ui.TclConsole;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class PasteTclTextToConsoleAction implements IEditorActionDelegate {

	private ISelection selection;

	private IEditorPart targetEditor;

	protected IDocument getDocument() {
		if (!(targetEditor instanceof ITextEditor))
			return null;

		ITextEditor editor = (ITextEditor) targetEditor;
		IDocumentProvider dp = editor.getDocumentProvider();
		return dp.getDocument(editor.getEditorInput());
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = targetEditor;
	}

	@Override
	public void run(IAction action) {
		ScriptConsoleManager manager = ScriptConsoleManager.getInstance();

		IScriptConsole console = manager.getActiveScriptConsole(TclConsole.CONSOLE_TYPE);

		if (console == null) {
			return;
		}

		if (selection instanceof ITextSelection) {
			String text = ((ITextSelection) selection).getText();
			console.insertText(text);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}
