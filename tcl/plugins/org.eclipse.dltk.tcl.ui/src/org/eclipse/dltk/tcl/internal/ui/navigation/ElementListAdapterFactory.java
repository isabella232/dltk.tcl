/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.navigation;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.ui.navigation.ElementsView.ElementList;

public class ElementListAdapterFactory implements IAdapterFactory {
	private static Class<?>[] PROPERTIES = new Class[] { IModelElement.class };

	public Class<?>[] getAdapterList() {
		return PROPERTIES;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object element, Class<T> key) {
		if (IModelElement.class.equals(key)) {
			if (element instanceof ElementList) {
				return (T) ((ElementList) element).getFirstElement();
			}
		}
		return null;
	}
}
