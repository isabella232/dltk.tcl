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
package org.eclipse.dltk.tcl.internal.tclchecker.impl;

public class SingleEnvironmentPredicate implements ISingleEnvironmentPredicate {

	public SingleEnvironmentPredicate(String environmentId) {
		this.environmentId = environmentId;
	}

	private final String environmentId;

	@Override
	public boolean evaluate(String environmentId) {
		return this.environmentId.equals(environmentId);
	}

	@Override
	public String getEnvironmentId() {
		return environmentId;
	}
}
