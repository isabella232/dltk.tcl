/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.definitions;

import java.io.IOException;
import java.net.URL;

import org.eclipse.dltk.tcl.definitions.Scope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class DefinitionLoader {
	public static Scope loadDefinitions(URL uri) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet
				.createResource(org.eclipse.emf.common.util.URI.createURI(uri
						.toString()));
		resource.load(uri.openStream(), null);
		return (Scope) resource.getContents().get(0);
	}
}
