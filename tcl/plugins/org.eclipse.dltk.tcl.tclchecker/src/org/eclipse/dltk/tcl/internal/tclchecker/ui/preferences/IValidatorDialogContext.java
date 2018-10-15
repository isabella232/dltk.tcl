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

import org.eclipse.dltk.tcl.internal.tclchecker.impl.IEnvironmentPredicate;
import org.eclipse.dltk.ui.environment.EnvironmentContainer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;

public interface IValidatorDialogContext {

	EnvironmentContainer getEnvironments();

	IEnvironmentPredicate getEnvironmentPredicate();

	boolean isNew();

	/**
	 * @return
	 */
	Shell getShell();

	IShellProvider getShellProvider();

	void setShellProvider(IShellProvider shellProvider);

	IValidationHandler getValidationHandler();

	/**
	 * @param validationHandler
	 */
	void setValidationHandler(IValidationHandler validationHandler);

}
