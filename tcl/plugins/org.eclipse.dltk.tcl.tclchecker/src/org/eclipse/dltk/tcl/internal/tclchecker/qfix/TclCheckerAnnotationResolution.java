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
package org.eclipse.dltk.tcl.internal.tclchecker.qfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.text.IAnnotationResolution;
import org.eclipse.dltk.ui.text.IAnnotationResolution2;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.texteditor.IEditorStatusLine;
import org.eclipse.ui.texteditor.ITextEditor;

public class TclCheckerAnnotationResolution
		implements IAnnotationResolution, IAnnotationResolution2, ITclCheckerQFixReporter {

	private final String replacement;
	private final ITextEditor editor;
	private final ISourceModule module;
	private final IMarker marker;

	public TclCheckerAnnotationResolution(String replacement, ITextEditor editor, ISourceModule module,
			IMarker marker) {
		this.replacement = replacement;
		this.editor = editor;
		this.module = module;
		this.marker = marker;
	}

	@Override
	public String getLabel() {
		return TclCheckerFixUtils.getLabel(replacement);
	}

	@Override
	public void run(IScriptAnnotation annotation, IDocument document) {
		if (editor.isDirty()) {
			final IResource resource = marker.getResource();
			final IMarkerFinder finder = TclCheckerFixUtils.createMarkerFinder(marker);
			editor.doSave(new NullProgressMonitor());
			// TODO revalidate only if not automatic
			if (TclCheckerFixUtils.revalidate(resource, module)) {
				final IMarker target = finder.find(resource);
				if (target != null) {
					TclCheckerFixUtils.updateDocument(target, document, replacement, this);
				} else {
					showError(Messages.TclCheckerAnnotationResolution_problemDisappeared);
				}
			} else {
				showError(Messages.TclCheckerAnnotationResolution_revalidationError);
			}
		} else {
			final IMarker target = TclCheckerFixUtils.verify(marker, module);
			if (target != null) {
				TclCheckerFixUtils.updateDocument(target, document, replacement, this);
			} else {
				showError(Messages.TclCheckerAnnotationResolution_problemDisappeared);
			}
		}
	}

	/**
	 * @param string
	 */
	@Override
	public void showError(String message) {
		final IEditorStatusLine statusLine = editor.getAdapter(IEditorStatusLine.class);
		if (statusLine != null) {
			statusLine.setMessage(true, message, null);
		}
	}

	@Override
	public String getDescription() {
		return TclCheckerFixUtils.getDescription(replacement);
	}
}
