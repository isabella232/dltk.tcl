package org.eclipse.dltk.tcl.internal.ui.packages;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class FixAllDependenciesActionDelegate
		implements IWorkbenchWindowActionDelegate {
	private ISelection selection;

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void run(IAction action) {
		if (this.selection == null) {
			return;
		}
		processSelectionToElements(selection);
	}

	public static void processResourcesToElements(Object o,
			Set<IProject> projects) {
		if (o instanceof IResource) {
			projects.add(((IResource) o).getProject());
		} else if (o instanceof IModelElement) {
			projects.add(((IModelElement) o).getScriptProject().getProject());
		}
	}

	protected void processSelectionToElements(ISelection selection) {
		final Set<IProject> projects = new HashSet<>();
		if (this.selection != null
				&& this.selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection) this.selection;
			for (Iterator<?> iterator = sel.iterator(); iterator.hasNext();) {
				Object o = iterator.next();
				processResourcesToElements(o, projects);
			}
		}
		if (!projects.isEmpty()) {
			final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell()) {
				@Override
				protected void configureShell(Shell shell) {
					super.configureShell(shell);
					shell.setText("Fix package dependencies...");
				}
			};
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() throws Exception {
					progressDialog.run(true, true, monitor -> {
						SubMonitor topMonitor = SubMonitor.convert(monitor,
								"Fixing dependencies", projects.size());
						final Set<IInterpreterInstall> installs = new HashSet<>();
						for (IProject project : projects) {
							IScriptProject scriptProject = DLTKCore
									.create(project);
							try {
								IInterpreterInstall install = ScriptRuntime
										.getInterpreterInstall(scriptProject);
								if (install != null && installs.add(install)) {
									TclPackagesManager
											.removeInterpreterInfo(install);
								}
								project.build(
										IncrementalProjectBuilder.FULL_BUILD,
										SubMonitor.convert(topMonitor,
												"Building " + project.getName(),
												1));
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}
					});
				}
			});
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}
