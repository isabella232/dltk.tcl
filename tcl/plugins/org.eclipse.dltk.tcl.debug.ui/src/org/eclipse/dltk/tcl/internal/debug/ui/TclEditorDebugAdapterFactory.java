/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.dltk.debug.ui.ScriptEditorDebugAdapterFactory;
import org.eclipse.dltk.debug.ui.breakpoints.ScriptToggleBreakpointAdapter;
import org.eclipse.dltk.tcl.internal.debug.ui.actions.IToggleSpawnpointsTarget;

/**
 * Debug adapter factory for the Tcl editor.
 */
public class TclEditorDebugAdapterFactory extends
		ScriptEditorDebugAdapterFactory {

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IRunToLineTarget.class,
				IToggleBreakpointsTarget.class, IToggleSpawnpointsTarget.class };
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType == IToggleSpawnpointsTarget.class) {
			return (T) getBreakpointAdapter();
		} else {
			return super.getAdapter(adaptableObject, adapterType);
		}
	}

	@Override
	protected ScriptToggleBreakpointAdapter getBreakpointAdapter() {
		return new TclToggleBreakpointAdapter();
	}
}
