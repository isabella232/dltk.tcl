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
package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.TodoTaskOptionsBlock;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class TclTodoTaskPreferencePage
		extends AbstractConfigurationBlockPropertyAndPreferencePage {

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription(TclPreferencesMessages.TodoTaskDescription);
	}

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new TodoTaskOptionsBlock(newStatusChangedListener, project,
				container, TclPlugin.PLUGIN_ID);
	}

	@Override
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected void setPreferenceStore() {
		// empty
	}

	@Override
	protected String getPreferencePageId() {
		return "org.eclipse.dltk.tcl.preferences.todo"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageId() {
		return "org.eclipse.dltk.tcl.propertyPage.todo"; //$NON-NLS-1$
	}

}
