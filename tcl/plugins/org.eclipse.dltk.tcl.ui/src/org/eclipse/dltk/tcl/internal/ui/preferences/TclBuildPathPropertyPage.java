/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.tcl.core.TclLanguageToolkit;
import org.eclipse.dltk.ui.preferences.BuildPathsPropertyPage;

public class TclBuildPathPropertyPage extends BuildPathsPropertyPage {
	public TclBuildPathPropertyPage() {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public IDLTKLanguageToolkit getLanguageToolkit() {
		return TclLanguageToolkit.getDefault();
	}
}
