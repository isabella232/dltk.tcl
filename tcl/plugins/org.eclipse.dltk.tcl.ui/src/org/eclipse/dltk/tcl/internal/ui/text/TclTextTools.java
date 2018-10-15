/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import org.eclipse.dltk.core.SimpleClassDLTKExtensionManager;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.ui.TclSemanticPositionUpdater;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.ui.semantilhighlighting.ISemanticHighlightingExtension;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.dltk.ui.editor.highlighting.ISemanticHighlightingUpdater;
import org.eclipse.dltk.ui.editor.highlighting.SemanticHighlighting;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.templates.TemplateVariableProcessor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.ui.texteditor.ITextEditor;

public class TclTextTools extends ScriptTextTools {

	private SimpleClassDLTKExtensionManager extensions = new SimpleClassDLTKExtensionManager(
			TclUI.PLUGIN_ID + ".tclSemanticHighlighting"); //$NON-NLS-1$

	private final static String[] LEGAL_CONTENT_TYPES = new String[] {
			TclPartitions.TCL_STRING, TclPartitions.TCL_COMMENT };

	public TclTextTools(boolean autoDisposeOnDisplayDispose) {
		super(TclPartitions.TCL_PARTITIONING, LEGAL_CONTENT_TYPES,
				autoDisposeOnDisplayDispose);
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		return new TclSourceViewerConfiguration(getColorManager(),
				preferenceStore, editor, partitioning);
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			TemplateVariableProcessor variableProcessor) {
		return new CodeTemplateTclSourceViewerConfiguration(getColorManager(),
				preferenceStore, null, variableProcessor);
	}

	@Override
	public IPartitionTokenScanner getPartitionScanner() {
		return new TclPartitionScanner();
	}

	private ISemanticHighlightingExtension[] getExtensions() {
		Object[] objects = extensions.getObjects();
		ISemanticHighlightingExtension[] exts = new ISemanticHighlightingExtension[objects.length];

		for (int i = 0; i < objects.length; i++) {
			exts[i] = ((ISemanticHighlightingExtension) objects[i]);
		}
		return exts;
	}

	@Override
	public SemanticHighlighting[] getSemanticHighlightings() {
		return getSemanticPositionUpdater(TclNature.NATURE_ID)
				.getSemanticHighlightings();
	}

	@Override
	public ISemanticHighlightingUpdater getSemanticPositionUpdater(
			String natureId) {
		return new TclSemanticPositionUpdater(getExtensions());
	}

	public final static class SH extends SemanticHighlighting {

		private final String preferenceKey;
		private final String bgColor;
		private final String description;

		public SH(String editorXmlTagNameColor, String bgColor, String desc) {
			this.preferenceKey = editorXmlTagNameColor;
			this.bgColor = bgColor;
			this.description = desc;
		}

		@Override
		public boolean isSemanticOnly() {
			return description != null;
		}

		@Override
		public String getPreferenceKey() {
			return preferenceKey;
		}

		@Override
		public String getBackgroundPreferenceKey() {
			return bgColor;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((preferenceKey == null) ? 0 : preferenceKey.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final SH other = (SH) obj;
			if (preferenceKey == null) {
				if (other.preferenceKey != null)
					return false;
			} else if (!preferenceKey.equals(other.preferenceKey))
				return false;
			return true;
		}

		@Override
		public String getDisplayName() {
			return description;
		}
	}
}
