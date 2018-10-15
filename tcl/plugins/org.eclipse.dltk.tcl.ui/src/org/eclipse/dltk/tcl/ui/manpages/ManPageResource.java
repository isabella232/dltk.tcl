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
package org.eclipse.dltk.tcl.ui.manpages;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * @since 2.0
 */
public interface ManPageResource extends Resource {

	public Documentation findDefault();

	public Documentation findByName(String name);

	public Documentation findById(String id);

	public boolean isEmpty();

	public List<Documentation> getDocumentations();

	public void checkDefault();

}
