package org.eclipse.dltk.tcl.internal.tclchecker.ui.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EnvironmentPathBlock extends Composite {
	// private Text path;
	private Table pathTable;
	private TableViewer pathViewer;

	/**
	 * Environment to path association.
	 */
	Map paths = new HashMap();

	public EnvironmentPathBlock(Composite parent) {
		super(parent, SWT.NONE);
	}
}
