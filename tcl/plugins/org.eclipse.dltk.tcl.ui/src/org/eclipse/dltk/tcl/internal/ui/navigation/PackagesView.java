/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.navigation;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IPackageDeclaration;

public class PackagesView extends ElementsView {
	@Override
	public String getElementName(Object element) {
		if (element instanceof IPackageDeclaration) {
			return ((IPackageDeclaration) element).getElementName();
		}
		return null;
	}

	@Override
	public String getJobTitle() {
		return "Packages view search...";
	}

	@Override
	public boolean isElement(IModelElement e) {
		return e instanceof IPackageDeclaration;
	}

	@Override
	public boolean needProcessChildren(IModelElement e) {
		return true;
	}

	@Override
	protected String getPreferencesId() {
		return "PackagesView_";
	}
}
