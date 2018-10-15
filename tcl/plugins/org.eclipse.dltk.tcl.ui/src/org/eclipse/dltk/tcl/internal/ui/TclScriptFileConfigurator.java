/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptFileConfigurator;
import org.eclipse.dltk.tcl.internal.ui.editor.TclEditor;
import org.eclipse.ui.ide.IDE;

public class TclScriptFileConfigurator implements IScriptFileConfigurator {

	@Override
	public void configure(IFile file) throws CoreException {
		final String value = file.getPersistentProperty(IDE.EDITOR_KEY);
		if (value == null) {
			IDE.setDefaultEditor(file, TclEditor.EDITOR_ID);
		}
	}

}
