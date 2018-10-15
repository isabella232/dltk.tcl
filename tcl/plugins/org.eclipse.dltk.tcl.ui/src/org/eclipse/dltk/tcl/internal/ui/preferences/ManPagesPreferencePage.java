/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package org.eclipse.dltk.tcl.internal.ui.preferences;

import org.eclipse.dltk.tcl.internal.ui.documentation.ManPagesLocationsBlock;
import org.eclipse.dltk.tcl.internal.ui.documentation.ManPagesMessages;
import org.eclipse.dltk.tcl.ui.manpages.dialogs.ManPagesConfigurationDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The page for setting the tcl documentation locations.
 */
public final class ManPagesPreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage {

	protected final ManPagesLocationsBlock fBlock = new ManPagesLocationsBlock(
			null, false);

	public ManPagesPreferencePage() {
		noDefaultAndApplyButton();
		setDescription(ManPagesMessages.ManPagesPreferencePage_0);
	}

	@Override
	protected Control createContents(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		fBlock.createControl(composite);
		final Composite buttons = new Composite(composite, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		final GridLayout buttonLayout = new GridLayout();
		buttonLayout.marginHeight = buttonLayout.marginWidth = 0;
		buttons.setLayout(buttonLayout);
		final Button configure = new Button(buttons, SWT.PUSH);
		configure.setText(ManPagesMessages.ManPagesPreferencePage_1);
		configure.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final ManPagesConfigurationDialog dialog = new ManPagesConfigurationDialog(
						getShell());
				if (dialog.open() == Window.OK) {
					fBlock.initialize();
				}
			}
		});
		setButtonLayoutData(configure);
		return composite;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			fBlock.initialize();
		}
	}

	@Override
	public void init(IWorkbench workbench) {
		// empty
	}
}
