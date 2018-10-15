/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.dltk.debug.ui.DLTKDebugUIPluginPreferenceInitializer;
import org.eclipse.dltk.tcl.core.TclNature;

public class TclDebugUIPreferenceInitializer extends
		DLTKDebugUIPluginPreferenceInitializer {

	@Override
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

}
