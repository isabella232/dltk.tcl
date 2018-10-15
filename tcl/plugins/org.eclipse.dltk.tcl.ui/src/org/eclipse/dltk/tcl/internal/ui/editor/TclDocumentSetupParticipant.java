/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.text.TclTextTools;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.jface.text.IDocument;

public class TclDocumentSetupParticipant implements IDocumentSetupParticipant {

	@Override
	public void setup(IDocument document) {
		TclTextTools tools = TclUI.getDefault().getTextTools();
		tools.setupDocumentPartitioner(document,
				TclPartitions.TCL_PARTITIONING);
	}
}
