/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker.ui.preferences;

import org.eclipse.dltk.tcl.internal.tclchecker.impl.IEnvironmentPredicate;
import org.eclipse.dltk.ui.environment.EnvironmentContainer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public class ValidatorDialogContext implements IValidatorDialogContext {

	private final IEnvironmentPredicate environmentPredicate;
	private final EnvironmentContainer environments;
	private final boolean isNew;

	public ValidatorDialogContext(IEnvironmentPredicate environmentPredicate, EnvironmentContainer environments,
			boolean isNew) {
		this.environmentPredicate = environmentPredicate;
		this.environments = environments;
		this.isNew = isNew;
	}

	@Override
	public IEnvironmentPredicate getEnvironmentPredicate() {
		return environmentPredicate;
	}

	@Override
	public EnvironmentContainer getEnvironments() {
		return environments;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	@Override
	public Shell getShell() {
		if (shellProvider == null) {
			SWT.error(SWT.ERROR_UNSPECIFIED);
		}
		return shellProvider.getShell();
	}

	private IShellProvider shellProvider = null;

	@Override
	public IShellProvider getShellProvider() {
		return shellProvider;
	}

	@Override
	public void setShellProvider(IShellProvider shellProvider) {
		this.shellProvider = shellProvider;
	}

	private IValidationHandler validationHandler;

	@Override
	public IValidationHandler getValidationHandler() {
		return validationHandler;
	}

	@Override
	public void setValidationHandler(IValidationHandler validationHandler) {
		this.validationHandler = validationHandler;
	}

}
