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
package org.eclipse.dltk.tcl.activestatedebugger.spawnpoint;

import java.util.List;
import java.util.Set;

public class SpawnpointCommands {

	private final List<String> commands;
	private final Set<String> selectedCommands;

	/**
	 * @param commands
	 * @param selectedCommands
	 */
	public SpawnpointCommands(List<String> commands,
			Set<String> selectedCommands) {
		this.commands = commands;
		this.selectedCommands = selectedCommands;
	}

	public List<String> getCommands() {
		return commands;
	}

	public Set<String> getSelectedCommands() {
		return selectedCommands;
	}

	public boolean isSelected(String command) {
		return selectedCommands.contains(command);
	}

}
