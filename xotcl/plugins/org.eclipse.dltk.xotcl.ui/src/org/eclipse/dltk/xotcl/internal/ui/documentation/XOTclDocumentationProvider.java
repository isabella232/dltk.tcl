/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.xotcl.internal.ui.documentation;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.tcl.internal.ui.documentation.ScriptDocumentationProvider;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.dltk.xotcl.core.documentation.XOTclDocumentationResolver;

public class XOTclDocumentationProvider extends ScriptDocumentationProvider
		implements IScriptDocumentationProvider {

	public Reader getInfo(IMember member, boolean lookIntoParents,
			boolean lookIntoExternal) {
		String header = XOTclDocumentationResolver.getDocumentationFor(member);
		if (header.trim().length() == 0) {
			return null;
		}
		// Lets look selected module for documentation for this element.
		return new StringReader(convertToHTML(header));
	}

	public Reader getInfo(String content) {
		return null;
	}
}
