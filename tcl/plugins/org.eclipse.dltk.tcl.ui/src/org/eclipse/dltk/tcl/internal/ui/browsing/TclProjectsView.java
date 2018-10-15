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
package org.eclipse.dltk.tcl.internal.ui.browsing;

import org.eclipse.dltk.ui.browsing.ProjectsView;
import org.eclipse.dltk.ui.viewsupport.DecoratingModelLabelProvider;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.viewers.IContentProvider;

public class TclProjectsView extends ProjectsView {

	@Override
	protected DecoratingModelLabelProvider createDecoratingLabelProvider(
			ScriptUILabelProvider provider) {
		return new TclDecoratingModelLabelProvider(provider);
	}

	@Override
	protected IContentProvider createContentProvider() {
		return new TclProjectAndSourceFolderContentProvider(this, getToolkit());
	}

}
