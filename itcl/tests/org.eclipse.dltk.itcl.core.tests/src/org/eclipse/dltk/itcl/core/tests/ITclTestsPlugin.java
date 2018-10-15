/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.itcl.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class ITclTestsPlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.dltk.itcl.core.tests"; //$NON-NLS-1$

	// The shared instance.
	private static ITclTestsPlugin plugin;

	/**
	 * The constructor.
	 */
	public ITclTestsPlugin() {
		plugin = this;
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static ITclTestsPlugin getDefault() {
		return plugin;
	}

}
