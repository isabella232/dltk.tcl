/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
package org.eclipse.dltk.tcl.activestatedebugger.preferences;

class PackageElement {
	final String packageName;

	public PackageElement(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public int hashCode() {
		return packageName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PackageElement) {
			final PackageElement other = (PackageElement) obj;
			return packageName.equals(other.packageName);
		} else {
			return false;
		}
	}
}