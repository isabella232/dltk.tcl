/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker.ui.preferences;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConstants;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerHelper;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerPlugin;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerProblemDescription;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;
import org.eclipse.dltk.ui.util.PixelConverter;
import org.eclipse.dltk.validators.ui.ValidatorConfigurationPage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;

public class TclCheckerConfigurationPage extends ValidatorConfigurationPage
		implements ISelectionChangedListener {
	// private static final String PREFERENCES_ID =
	// "org.eclipse.dltk.tcl.tclchecker.ui.preferences.TclCheckerPreferences";

	// private Text path;
	private Table pathTable;
	private TableViewer pathViewer;

	/**
	 * Environment to path association.
	 */
	Map paths = new HashMap();
	private Map pcxPaths;

	private Button errorsMode;
	private Button errorsAndUsageWarningsMode;
	private Button allMode;

	private Table problemsTable;

	private String message = "";
	private int messageType = IStatus.OK;

	private ListViewer lview;
	private Button noPCX;

	private SelectionAdapter noPCXSelectionListener;

	private Button fEditButton;

	private Map noPCXValues;

	private Group pcxGroup;
	private Button pcxAdd;
	private Button pcxRemove;

	private class PathLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof IEnvironment) {
				switch (columnIndex) {
				case 0:
					return ((IEnvironment) element).getName();
				case 1:
					Object path = paths.get(((IEnvironment) element));
					if (path != null) {
						return (String) path;
					}
					return "(undefined)";
				default:
					break;
				}
			}
			return null;
		}
	}

	public void createControl(Composite parent, int columns) {
		Composite c = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = columns;
		c.setLayoutData(gd);
		c.setLayout(new FillLayout());
		createContents(c);
		initializeValues();

		validateTclCheckerPath();

	}

	public IStatus getStatus() {
		return new Status(messageType, TclCheckerPlugin.PLUGIN_ID, message);
	}

	protected void setModeSelection(int mode) {
		errorsMode.setSelection(mode == TclCheckerConstants.MODE_ERRORS);
		errorsAndUsageWarningsMode
				.setSelection(mode == TclCheckerConstants.MODE_ERRORS_AND_USAGE_WARNINGS);
		allMode.setSelection(mode == TclCheckerConstants.MODE_ALL);
	}

	protected int getModeSelection() {
		if (errorsMode.getSelection()) {
			return TclCheckerConstants.MODE_ERRORS;
		} else if (errorsAndUsageWarningsMode.getSelection()) {
			return TclCheckerConstants.MODE_ERRORS_AND_USAGE_WARNINGS;
		} else if (allMode.getSelection()) {
			return TclCheckerConstants.MODE_ALL;
		}

		return -1;
	}

	public void validateTclCheckerPath() {
		// String txtPath = path.getText().trim();
		//
		// if ("".equals(txtPath)) {
		// setMessage(PreferencesMessages.TclChecker_path_isempty,
		// IStatus.WARNING);
		// updateStatus();
		// return;
		// }
		//
		// IPath path = Path.fromOSString(txtPath);
		// File file = PlatformFileUtils.findAbsoluteOrEclipseRelativeFile(path
		// .toFile());
		//
		// if (!path.isValidPath(path.toOSString())) {
		// setMessage(PreferencesMessages.TclChecker_path_isinvalid,
		// IStatus.ERROR);
		// } else if (!file.isFile()) {
		// setMessage(PreferencesMessages.TclChecker_path_notexists,
		// IStatus.ERROR);
		// } else if (!file.exists()) {
		// setMessage(PreferencesMessages.TclChecker_path_notexists,
		// IStatus.ERROR);
		// } else if (txtPath.indexOf("tclchecker") == -1) {
		// setMessage(PreferencesMessages.TclChecker_path_notlookslike,
		// IStatus.WARNING);
		// } else {
		// setMessage(null);
		// }
		// updateStatus();
	}

	private void setMessage(Object object) {
		this.message = "";
		this.messageType = IStatus.OK;
	}

	private void setMessage(String message, int type) {
		this.message = message;
		this.messageType = type;
	}

	// public void validatePCXTclCheckerPath() {
	// String txtPath = pcxPath.getText().trim();
	//
	// if ("".equals(txtPath)) {
	// setMessage(null);
	// updateStatus();
	// return;
	// }
	//
	// IPath path = Path.fromOSString(txtPath);
	// File file = PlatformFileUtils.findAbsoluteOrEclipseRelativeFile(path
	// .toFile());
	//
	// if (!path.isValidPath(path.toOSString())) {
	// setMessage(PreferencesMessages.TclChecker_path_isinvalid,
	// IStatus.WARNING);
	// } else if (!file.isDirectory()) {
	// setMessage(PreferencesMessages.TclChecker_path_notexists,
	// IStatus.WARNING);
	// } else if (!file.exists()) {
	// setMessage(PreferencesMessages.TclChecker_path_notexists,
	// IStatus.WARNING);
	// } else {
	// setMessage(null);
	// }
	// updateStatus();
	// }

	protected void createModeGroup(Composite parent, Object data) {
		Group radioGroup = new Group(parent, SWT.NONE);
		radioGroup.setText(PreferencesMessages.TclChecker_mode);
		radioGroup.setLayoutData(data);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		radioGroup.setLayout(layout);

		errorsMode = new Button(radioGroup, SWT.RADIO);
		errorsMode.setText(PreferencesMessages.TclChecker_mode_errors);

		errorsAndUsageWarningsMode = new Button(radioGroup, SWT.RADIO);
		errorsAndUsageWarningsMode
				.setText(PreferencesMessages.TclChecker_mode_errorsAndUsageWarnings);

		allMode = new Button(radioGroup, SWT.RADIO);
		allMode.setText(PreferencesMessages.TclChecker_mode_all);
	}

	protected void createPathGroup(final Composite parent, Object data) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PreferencesMessages.TclChecker_path);
		group.setLayoutData(data);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		GridData dt = new GridData(SWT.FILL, SWT.FILL, true, true);
		dt.horizontalSpan = 1;
		group.setLayoutData(dt);

		createPathTable(parent, group);
	}

	protected void editPDX() {

	}

	protected void editPath() {
		ISelection selection = pathViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			IEnvironment environment = (IEnvironment) sel.getFirstElement();
			IEnvironmentUI ui = (IEnvironmentUI) environment
					.getAdapter(IEnvironmentUI.class);
			String file = ui.selectFile(this.pathTable.getShell(),
					IEnvironmentUI.EXECUTABLE);
			if (file != null) {
				this.paths.put(environment, file);
				this.pathViewer.refresh();
			}
		}
	}

	private void createPathTable(final Composite parent, Group group) {
		PixelConverter conv = new PixelConverter(parent);

		pathTable = new Table(group, SWT.SINGLE | SWT.BORDER
				| SWT.FULL_SELECTION);
		pathTable.setHeaderVisible(true);
		pathTable.setLinesVisible(true);
		GridData tableData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		tableData.heightHint = conv.convertHeightInCharsToPixels(8);
		pathTable.setLayoutData(tableData);

		pathViewer = new TableViewer(pathTable);

		TableViewerColumn environmentsColumn = new TableViewerColumn(
				pathViewer, SWT.NULL);
		environmentsColumn.getColumn().setText("Environment:");
		environmentsColumn.getColumn().setWidth(
				conv.convertWidthInCharsToPixels(20));
		TableViewerColumn pathColumn = new TableViewerColumn(pathViewer,
				SWT.NULL);
		pathColumn.getColumn().setText("Path:");
		pathColumn.getColumn().setWidth(conv.convertWidthInCharsToPixels(70));
		pathColumn.setEditingSupport(new EditingSupport(pathViewer) {
			protected boolean canEdit(Object element) {
				return true;
			}

			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(pathTable) {
					private Button browse;

					protected Control createControl(Composite parent) {
						Composite composite = new Composite(parent,SWT.NONE);
						composite.setBackground(parent.getBackground());
						GridLayout layout = new GridLayout(2, false);
						layout.marginLeft = -4;
						layout.marginTop = -4;
						layout.marginBottom = -4;
						layout.marginRight = -4;
						composite.setLayout(layout);
						super.createControl(composite);
						text.setLayoutData(new GridData(SWT.FILL,SWT.DEFAULT, true, false));
						browse = new Button(composite,SWT.PUSH);
						browse.setText("...");
						Font font = new Font(parent.getDisplay(), "arial", 6, 0);
						browse.setFont(font);
						browse.setLayoutData(new GridData(SWT.DEFAULT,SWT.FILL, false, true));
						browse.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								editPath();
							}
						});
						return composite;
					}

					protected void focusLost() {
						if(!text.isFocusControl() && !browse.isFocusControl()) {
							super.focusLost();
						}
					}
				};
			}

			protected Object getValue(Object element) {
				return paths.get(getEnvironment());
			}

			protected void setValue(Object element, Object value) {
				paths.put(getEnvironment(), value);
				pathViewer.refresh();
			}
		});

		pathViewer.setLabelProvider(new PathLabelProvider());
		pathViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof IEnvironment[]) {
					return (Object[]) inputElement;
				}
				return new Object[0];
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
		pathViewer.setInput(EnvironmentManager.getEnvironments());
		pathViewer.addSelectionChangedListener(this);
	}

	protected void createPCXPathGroup(final Composite parent, Object data) {
		pcxGroup = new Group(parent, SWT.NONE);
		pcxGroup.setText(PreferencesMessages.TclChecker_pcxPath);
		pcxGroup.setLayoutData(data);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pcxGroup.setLayout(layout);

		noPCX = new Button(pcxGroup, SWT.CHECK);
		noPCX.setText("Disable Using of PCX files");
		GridData noPCXDG = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		noPCXDG.horizontalSpan = 2;
		noPCX.setLayoutData(noPCXDG);

		final org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(
				pcxGroup, SWT.BORDER);
		lview = new ListViewer(list);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		lview.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				IEnvironment environment = getEnvironment();
				if (inputElement instanceof Map) {
					if (environment != null) {
						return ((List) pcxPaths.get(environment)).toArray();
					}
				}
				return new Object[0];
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});

		lview.setLabelProvider(new LabelProvider());

		Composite buttons = new Composite(pcxGroup, SWT.NONE);
		RowLayout row = new RowLayout(SWT.VERTICAL);
		row.fill = true;
		buttons.setLayout(row);
		pcxAdd = new Button(buttons, SWT.PUSH);
		pcxAdd.setText("Add");
		pcxAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IEnvironment environment = getEnvironment();
				IEnvironmentUI ui = (IEnvironmentUI) environment
						.getAdapter(IEnvironmentUI.class);
				String path = ui.selectFolder(pcxAdd.getShell());
				if (path != null) {
					((List) pcxPaths.get(environment)).add(path);
				}
				updatePCX();
			}
		});
		pcxRemove = new Button(buttons, SWT.PUSH);
		pcxRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = lview.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ssel = (IStructuredSelection) selection;
					for (Iterator i = ssel.iterator(); i.hasNext();) {
						String s = (String) i.next();
						IEnvironment environment = getEnvironment();
						((List) pcxPaths.get(environment)).remove(s);
					}
				}
				updatePCX();
			}
		});
		pcxRemove.setText("Remove");

		lview.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = lview.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ssel = (IStructuredSelection) selection;
					boolean empty = ssel.isEmpty();
					pcxRemove.setEnabled(!empty);
				}
			}
		});
		noPCXSelectionListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean selection = noPCX.getSelection();

				IStructuredSelection pathSelection = (IStructuredSelection) pathViewer
						.getSelection();
				boolean enabled = !pathSelection.isEmpty();

				list.setEnabled(!selection && enabled);
				pcxAdd.setEnabled(!selection && enabled);
				pcxRemove.setEnabled(!selection && enabled);
			}
		};
		noPCX.addSelectionListener(noPCXSelectionListener);
	}

	protected IEnvironment getEnvironment() {
		IStructuredSelection selection = (IStructuredSelection) pathViewer
				.getSelection();
		if (selection.isEmpty()) {
			return EnvironmentManager
					.getEnvironmentById(LocalEnvironment.ENVIRONMENT_ID);
		}
		return (IEnvironment) selection.getFirstElement();
	}

	protected void setSelection(boolean value) {
		TableItem[] items = problemsTable.getItems();
		for (int i = 0; i < items.length; ++i) {
			items[i].setChecked(value);
		}
	}

	protected void invertSelection() {
		TableItem[] items = problemsTable.getItems();
		for (int i = 0; i < items.length; ++i) {
			items[i].setChecked(!items[i].getChecked());
		}
	}

	protected void createSuppressProblemsGroup(Composite parent, Object data) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PreferencesMessages.TclChecker_suppressProblems);
		group.setLayoutData(data);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		problemsTable = new Table(group, SWT.V_SCROLL | SWT.CHECK
				| SWT.HIDE_SELECTION);
		problemsTable.setBounds(0, 0, 150, 200);
		problemsTable.setHeaderVisible(false);

		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0);
		tableData.heightHint = 100;
		problemsTable.setLayoutData(tableData);

		// Columns
		TableColumn problemsColumn = new TableColumn(problemsTable, SWT.LEFT);
		problemsColumn.setWidth(200);

		TableColumn typesColumn = new TableColumn(problemsTable, SWT.LEFT);
		typesColumn.setWidth(100);

		// Items
		List problems = TclCheckerProblemDescription.getProblemIdentifiers();
		Collections.sort(problems);
		Iterator it = problems.iterator();
		while (it.hasNext()) {
			TableItem item = new TableItem(problemsTable, SWT.NONE);
			String problemId = (String) it.next();

			item.setData(problemId);

			String type = TclCheckerProblemDescription
					.getProblemType(problemId);
			int category = TclCheckerProblemDescription
					.matchProblemCategory(type);

			String typeString = "";
			if (TclCheckerProblemDescription.isError(category)) {
				typeString = PreferencesMessages.TclChecker_error;
			} else if (TclCheckerProblemDescription.isWarning(category)) {
				typeString = PreferencesMessages.TclChecker_warning;
			}

			item.setText(new String[] { problemId, typeString });
		}

		// Buttons composite
		Composite buttonsComposite = new Composite(group, SWT.NULL);
		RowLayout buttonsLayout = new RowLayout();
		buttonsLayout.type = SWT.VERTICAL;
		buttonsLayout.fill = true;
		buttonsComposite.setLayout(buttonsLayout);

		GridData buttonsData = new GridData();
		buttonsData.verticalAlignment = SWT.TOP;
		buttonsComposite.setLayoutData(buttonsData);

		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText(PreferencesMessages.TclChecker_selectAll);
		selectAll.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSelection(true);
			}
		});

		Button clearSelection = new Button(buttonsComposite, SWT.PUSH);
		clearSelection.setText(PreferencesMessages.TclChecker_clearSelection);
		clearSelection.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSelection(false);
			}
		});

		Button invertSelection = new Button(buttonsComposite, SWT.PUSH);
		invertSelection.setText(PreferencesMessages.TclChecker_invertSelection);
		invertSelection.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = problemsTable.getItems();
				for (int i = 0; i < items.length; ++i) {
					items[i].setChecked(!items[i].getChecked());
				}
			}
		});
	}

	public Control createContents(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;

		top.setLayout(layout);

		createPathGroup(top, new GridData(GridData.FILL, SWT.NONE, true, false));
		Composite ctrl = new Composite(top, SWT.NONE);
		ctrl.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, false));
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		gl.marginLeft = -5;
		gl.marginTop = -5;
		gl.marginRight = -5;
		ctrl.setLayout(gl);

		createModeGroup(ctrl, new GridData(SWT.DEFAULT, SWT.FILL, false, false));
		createPCXPathGroup(ctrl, new GridData(GridData.FILL, SWT.FILL, true,
				true));
		createSuppressProblemsGroup(top, new GridData(GridData.FILL,
				GridData.FILL, true, true));

		initializeValues();

		validateTclCheckerPath();

		return top;
	}

	public void init(IWorkbench workbench) {

	}

	protected IPreferenceStore doGetPreferenceStore() {
		return TclCheckerPlugin.getDefault().getPreferenceStore();
	}

	public void initializeValues() {
		IPreferenceStore store = doGetPreferenceStore();

		// Path
		// path.setText(store.getString(TclCheckerConstants.PREF_PATH));
		this.paths = TclCheckerHelper.getPaths(store);
		this.pathViewer.refresh();
		this.pcxPaths = TclCheckerHelper.getPcxPaths(store);
		this.noPCXValues = TclCheckerHelper.getNoPCX(store);

		// this.noPCX.setSelection(store
		// .getBoolean(TclCheckerConstants.PREF_NO_PCX));
		// noPCXSelectionListener.widgetSelected(null);

		lview.setInput(this.pcxPaths);
		updatePCX();

		// Mode
		setModeSelection(store.getInt(TclCheckerConstants.PREF_MODE));

		// Problems
		TableItem[] items = problemsTable.getItems();
		for (int i = 0; i < items.length; ++i) {
			TableItem item = items[i];
			item.setChecked(store.getBoolean((String) (item.getData())));
		}
	}

	protected void performDefaults() {
		setModeSelection(TclCheckerConstants.MODE_ALL);
		setSelection(false);
	}

	public void applyChanges() {
		IPreferenceStore store = doGetPreferenceStore();

		// Path
		// store.setValue(TclCheckerConstants.PREF_PATH, path.getText());
		// store.setValue(TclCheckerConstants.PREF_PCX_PATH, pcxPath.getText());
		TclCheckerHelper.setPaths(store, paths);
		TclCheckerHelper.setPcxPaths(store, pcxPaths);

		// store.setValue(TclCheckerConstants.PREF_NO_PCX, this.noPCX
		// .getSelection());

		// Mode
		store.setValue(TclCheckerConstants.PREF_MODE, getModeSelection());

		// Problems
		TableItem[] items = problemsTable.getItems();
		for (int i = 0; i < items.length; ++i) {
			TableItem item = items[i];
			store.setValue((String) item.getData(), item.getChecked());
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) pathViewer
				.getSelection();
		boolean enabled = !selection.isEmpty();
		pcxGroup.setEnabled(enabled);
		lview.getControl().setEnabled(enabled);
		noPCX.setEnabled(enabled);

		updatePCX();
	}

	private void updatePCX() {
		lview.refresh();
		noPCXSelectionListener.widgetSelected(null);
	}
}
