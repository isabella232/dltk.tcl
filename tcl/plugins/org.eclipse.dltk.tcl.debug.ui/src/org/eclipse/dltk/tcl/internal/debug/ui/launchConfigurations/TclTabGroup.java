/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.launchConfigurations;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.dltk.debug.ui.launchConfigurations.ScriptCommonTab;
import org.eclipse.dltk.tcl.internal.debug.ui.interpreters.TclInterpreterTab;

public class TclTabGroup extends AbstractLaunchConfigurationTabGroup {
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		TclMainLaunchConfigurationTab main = new TclMainLaunchConfigurationTab(mode);
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { main, new TclScriptArgumentsTab(),
				new TclInterpreterTab(main), new TclEnvironmentTab(),
				// new SourceContainerLookupTab(),
				// new CommonTab()
				new ScriptCommonTab(main) };
		setTabs(tabs);
	}
}
