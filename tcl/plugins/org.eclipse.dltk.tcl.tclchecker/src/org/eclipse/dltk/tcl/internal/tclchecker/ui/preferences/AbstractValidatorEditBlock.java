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

public abstract class AbstractValidatorEditBlock implements IValidatorEditBlock {

	private IValidatorDialogContext context;

	@Override
	public final void init(IValidatorDialogContext context, Object object) {
		this.context = context;
		doInit(context, object);
	}

	protected IValidatorDialogContext getContext() {
		return context;
	}

	/**
	 * @param context
	 * @param object
	 */
	protected abstract void doInit(IValidatorDialogContext context, Object object);

	protected final void validate() {
		validate(null);
	}

	protected void validate(Object hint) {
		final IValidationHandler handler = context.getValidationHandler();
		if (handler != null) {
			handler.validate(hint);
		}
	}

}
