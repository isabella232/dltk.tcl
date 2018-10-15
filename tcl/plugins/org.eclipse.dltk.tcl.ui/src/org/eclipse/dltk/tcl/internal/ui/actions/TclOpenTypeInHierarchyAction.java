/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.actions;

import org.eclipse.dltk.tcl.internal.ui.TclUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction;

public class TclOpenTypeInHierarchyAction extends OpenTypeInHierarchyAction {
	@Override
	protected IDLTKUILanguageToolkit getLanguageToolkit() {
		return TclUILanguageToolkit.getInstance();
	}
}
