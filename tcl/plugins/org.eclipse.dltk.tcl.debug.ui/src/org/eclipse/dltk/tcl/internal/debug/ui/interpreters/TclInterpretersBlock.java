/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.caching.ArchiveContentCacheProvider;
import org.eclipse.dltk.core.caching.IContentCache;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.internal.debug.ui.interpreters.InterpretersBlock;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.dltk.tcl.core.packages.TclPackageInfo;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class TclInterpretersBlock extends InterpretersBlock {
	private Button fetchInterpreterInformation;

	@Override
	protected AddScriptInterpreterDialog createInterpreterDialog(IInterpreterInstall standin) {
		AddTclInterpreterDialog dialog = new AddTclInterpreterDialog(this, getShell(),
				ScriptRuntime.getInterpreterInstallTypes(getCurrentNature()), standin);
		return dialog;
	}

	@Override
	protected String getCurrentNature() {
		return TclNature.NATURE_ID;
	}

	@Override
	public void createControl(Composite ancestor) {
		super.createControl(ancestor);
		fetchInterpreterInformation = createPushButton(buttons, "Fetch information");
		fetchInterpreterInformation.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = fInterpreterList.getStructuredSelection();
				final IInterpreterInstall install = (IInterpreterInstall) selection.getFirstElement();
				fetchInterpreterInformation(install);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		enableButtons();
	}

	@Override
	protected void enableButtons() {
		super.enableButtons();
		if (fetchInterpreterInformation != null) {
			IStructuredSelection selection = fInterpreterList.getStructuredSelection();
			int selectionCount = selection.size();
			if (selectionCount == 1) {
				fetchInterpreterInformation.setEnabled(true);
			} else {
				fetchInterpreterInformation.setEnabled(false);
			}
		}
	}

	private void fetchInterpreterInformation(final IInterpreterInstall install) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(fetchInterpreterInformation.getShell());
		try {
			dialog.run(true, true, monitor -> {
				IContentCache cache = ModelManager.getModelManager().getCoreCache();
				monitor.beginTask("Fetching interpreter information", 100);

				List<TclPackageInfo> list = null;
				try {
					TclPackagesManager.markInterprterAsNotFetched(install);
					list = TclPackagesManager.getPackageInfos(install);
				} catch (Exception e1) {
					// Try one more time
					list = TclPackagesManager.getPackageInfos(install);
				}
				monitor.worked(20);

				SubProgressMonitor smon0 = new SubProgressMonitor(monitor, 40);
				int s = 0;
				int lsize = list.size();
				smon0.beginTask("Fetch package information", lsize / 50);
				while (s < lsize) {
					int part = 50;
					if (lsize - s < part) {
						part = lsize - s;
					}
					if (smon0.isCanceled()) {
						break;
					}
					smon0.subTask("Processing packages info (" + (lsize - s) + " left)");
					Set<String> pkgs = new HashSet<>();
					for (int i = s; i < s + part; i++) {
						pkgs.add(list.get(i).getName());
					}
					try {
						List<TclPackageInfo> infos = TclPackagesManager.getPackageInfos(install, pkgs, true);
					} catch (Exception e2) {
						if (DLTKCore.DEBUG) {
							e2.printStackTrace();
						}
					}
					s += part;
					smon0.worked(1);
				}

				smon0.done();

				SubProgressMonitor smon = new SubProgressMonitor(monitor, 40);
				IEnvironment env = install.getEnvironment();
				smon.beginTask("Processing packages", lsize);
				int index = 1;
				for (TclPackageInfo tclPackageInfo : list) {
					smon.subTask("Processing package:" + tclPackageInfo.getName() + " (" + (lsize - index) + " left)");
					index++;
					if (smon.isCanceled()) {
						break;
					}
					boolean processed = false;
					try {
						TclPackageInfo info = TclPackagesManager.getPackageInfo(install, tclPackageInfo.getName(),
								true);
						if (info != null) {
							List<String> sources = info.getSources();
							Set<IPath> parents = new HashSet<>();
							for (String source : sources) {
								IPath path1 = new Path(source);
								IPath parent = path1.removeLastSegments(1);
								parents.add(parent);
							}
							for (IPath path2 : parents) {
								IFileHandle file = env.getFile(path2);
								if (file.exists()) {
									ArchiveContentCacheProvider.processFolderIndexes(file, cache, smon);
									processed = true;
								}
							}
						}
					} catch (Exception e3) {
						if (DLTKCore.DEBUG) {
							e3.printStackTrace();
						}
					}
					if (!processed) {
						smon.worked(1);
					}
				}
				smon.done();
				monitor.done();
			});
		} catch (Exception e1) {
			if (DLTKCore.DEBUG) {
				e1.printStackTrace();
			}
		}
	}
}
