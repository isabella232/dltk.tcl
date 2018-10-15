/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.internal.tclchecker.ui.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance;
import org.eclipse.dltk.ui.dialogs.StatusInfo;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TclCheckerInstanceDialog extends StatusDialog {

	/**
	 * @param parent
	 * @param instance
	 */
	public TclCheckerInstanceDialog(Shell parent, IValidatorDialogContext context, CheckerInstance instance) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		context.setShellProvider(this);
		context.setValidationHandler(validationHandler);
		block.init(context, instance);
	}

	private IValidatorEditBlock block = new TclCheckerInstanceBlock();

	private IValidationHandler validationHandler = hint -> {
		final IStatus status = block.isValid(hint);
		if (status != null) {
			updateStatus(status);
		} else {
			updateStatus(StatusInfo.OK_STATUS);
		}
	};

	@Override
	public Control createDialogArea(Composite parent) {
		final Composite dialogArea = (Composite) super.createDialogArea(parent);
		final Composite content = new Composite(dialogArea, SWT.NONE);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		block.createControl(content);
		return dialogArea;
	}

	@Override
	public void create() {
		super.create();
		block.initControls();
		final IStatus status = block.isValid(null);
		if (status != null) {
			// TODO fix error status?
			updateStatus(status);
		}
	}

	@Override
	protected void okPressed() {
		IStatus status = block.isValid(null);
		if (status == null || status.isOK()) {
			block.saveValues();
			super.okPressed();
		}
	}
}
