/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
