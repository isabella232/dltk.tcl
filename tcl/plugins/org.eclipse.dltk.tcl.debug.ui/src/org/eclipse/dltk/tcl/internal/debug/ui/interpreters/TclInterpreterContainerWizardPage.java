/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterComboBlock;
import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterContainerWizardPage;
import org.eclipse.dltk.internal.debug.ui.interpreters.IInterpreterComboBlockContext;
import org.eclipse.dltk.tcl.core.TclNature;

public class TclInterpreterContainerWizardPage extends AbstractInterpreterContainerWizardPage {

	@Override
	protected AbstractInterpreterComboBlock createInterpreterBlock(IInterpreterComboBlockContext context) {
		final TclInterpreterComboBlock block = new TclInterpreterComboBlock(context);
		block.initialize(getScriptProject());
		return block;
	}

	@Override
	public String getScriptNature() {
		return TclNature.NATURE_ID;
	}
}
