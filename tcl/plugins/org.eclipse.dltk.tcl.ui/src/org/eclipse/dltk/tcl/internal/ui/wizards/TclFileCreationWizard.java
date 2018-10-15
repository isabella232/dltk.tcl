/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.wizards;

import org.eclipse.dltk.tcl.internal.ui.TclImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.NewSourceModulePage;
import org.eclipse.dltk.ui.wizards.NewSourceModuleWizard;

public class TclFileCreationWizard extends NewSourceModuleWizard {
	public static final String ID_WIZARD = "org.eclipse.dltk.tcl.internal.ui.wizards.TclFileCreationWizard"; //$NON-NLS-1$

	public TclFileCreationWizard() {
		setDefaultPageImageDescriptor(TclImages.DESC_WIZBAN_FILE_CREATION);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		// setWindowTitle(TclWizardMessages.NewFileWizard_title);
		setWindowTitle(TclWizardMessages.TclFileCreationWizardTitle);
	}

	@Override
	protected NewSourceModulePage createNewSourceModulePage() {
		return new TclFileCreationPage();
	}
}
