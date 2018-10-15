/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/

package org.eclipse.dltk.tcl.formatter.preferences;

import org.eclipse.dltk.ui.formatter.FormatterModifyDialog;
import org.eclipse.dltk.ui.formatter.IFormatterModifyDialogOwner;
import org.eclipse.dltk.ui.formatter.IScriptFormatterFactory;

public class TclFormatterModifyDialog extends FormatterModifyDialog {

	public TclFormatterModifyDialog(IFormatterModifyDialogOwner dialogOwner,
			IScriptFormatterFactory formatterFactory) {
		super(dialogOwner, formatterFactory);
		setTitle("Tcl Formatter");
	}
	
	@Override
	protected void addPages() {
		addTabPage("Indentation", new TclFormatterIndentationTabPage(this));
		addTabPage("Blank Lines", new TclFormatterBlankLinesPage(this));
		addTabPage("Comments", new TclFormatterCommentsPage(this));
	}
}
