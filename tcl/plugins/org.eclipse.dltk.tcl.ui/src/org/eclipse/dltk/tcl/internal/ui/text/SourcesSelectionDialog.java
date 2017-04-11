package org.eclipse.dltk.tcl.internal.ui.text;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class SourcesSelectionDialog extends Dialog {

	private final class SourcesLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof String) {
				return (String) element;
			}
			return ""; //$NON-NLS-1$
		}
	}

	private final class SourcesContentProvider
			implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer viewer, Object oldInput,
				Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return sources.toArray();
		}
	}

	private ListViewer sourcesViewer;
	private Set<String> sources = new HashSet<>();
	private IEnvironmentUI environmentUI;
	private Button remove;
	private Button add;

	protected SourcesSelectionDialog(IShellProvider parentShell,
			IEnvironment environment) {
		super(parentShell);
		this.environmentUI = environment.getAdapter(IEnvironmentUI.class);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Files Selection Dialog");
		Composite contents = (Composite) super.createDialogArea(parent);
		contents.setLayout(new GridLayout(2, false));
		sourcesViewer = new ListViewer(contents, SWT.SINGLE | SWT.BORDER);
		sourcesViewer.setLabelProvider(new SourcesLabelProvider());
		sourcesViewer.setContentProvider(new SourcesContentProvider());
		sourcesViewer.setInput(sources);
		sourcesViewer.getControl()
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite buttons = new Composite(contents, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.DEFAULT, SWT.FILL, false, true));
		buttons.setLayout(new GridLayout(1, false));
		add = new Button(buttons, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String file = environmentUI.selectFile(
						sourcesViewer.getList().getShell(),
						IEnvironmentUI.DEFAULT);
				if (file != null) {
					sources.add(file);
					sourcesViewer.refresh();
				}
			}
		});
		add.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		remove = new Button(buttons, SWT.PUSH);
		remove.setText("Remove");
		remove.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = getSelection();
				if (path != null) {
					sources.remove(path);
					sourcesViewer.refresh();
				}
			}
		});
		updateEnablement();
		sourcesViewer.addSelectionChangedListener(event -> updateEnablement());
		getShell().layout();
		return contents;
	}

	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		if (size.x < 400) {
			size.x = 400;
		}
		if (size.y < 300) {
			size.y = 300;
		}
		return size;
	}

	private String getSelection() {
		IStructuredSelection selection = (IStructuredSelection) sourcesViewer
				.getSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return (String) selection.getFirstElement();
	}

	private void updateEnablement() {
		String path = getSelection();
		if (path == null) {
			// edit.setEnabled(false);
			remove.setEnabled(false);
		} else {
			// edit.setEnabled(true);
			remove.setEnabled(true);
		}
	}

	/**
	 * @since 2.0
	 */
	public void setSources(Collection<String> value) {
		sources.clear();
		sources.addAll(value);
	}

	/**
	 * @since 2.0
	 */
	public Collection<String> getSources() {
		return Collections.unmodifiableCollection(sources);
	}
}
