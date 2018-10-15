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
import org.eclipse.dltk.core.CorrectionEngine;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerMarker;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.text.IScriptCorrectionContext;
import org.eclipse.dltk.ui.text.IScriptCorrectionProcessor;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;

public class TclCheckerCorrectionProcessor implements IScriptCorrectionProcessor {

	@Override
	public boolean canFix(IScriptAnnotation annotation) {
		if (TclCheckerMarker.TYPE.equals(annotation.getMarkerType()) && annotation instanceof SimpleMarkerAnnotation) {
			return isFixable(((SimpleMarkerAnnotation) annotation).getMarker());
		}
		return false;
	}

	public static boolean isFixable(IMarker marker) {
		final String[] corrections = CorrectionEngine
				.decodeArguments(marker.getAttribute(TclCheckerMarker.SUGGESTED_CORRECTIONS, null));
		return corrections != null && corrections.length != 0;
	}

	@Override
	public boolean canFix(IMarker marker) {
		if (TclCheckerMarker.TYPE.equals(MarkerUtilities.getMarkerType(marker))) {
			return isFixable(marker);
		}
		return false;
	}

	@Override
	public void computeQuickAssistProposals(IScriptAnnotation annotation, IScriptCorrectionContext context) {
		if (TclCheckerMarker.TYPE.equals(annotation.getMarkerType()) && annotation instanceof SimpleMarkerAnnotation) {
			computeQuickFixProposals(((SimpleMarkerAnnotation) annotation).getMarker(), annotation, context);
		}
	}

	@Override
	public void computeQuickAssistProposals(IMarker marker, IScriptCorrectionContext context) {
		if (TclCheckerMarker.TYPE.equals(MarkerUtilities.getMarkerType(marker))) {
			computeQuickFixProposals(marker, null, context);
		}
	}

	/**
	 * @param marker
	 * @param context
	 */
	public static void computeQuickFixProposals(IMarker marker, IScriptAnnotation annotation,
			IScriptCorrectionContext context) {
		final String[] corrections = CorrectionEngine
				.decodeArguments(marker.getAttribute(TclCheckerMarker.SUGGESTED_CORRECTIONS, null));
		if (corrections != null) {
			for (int i = 0; i < corrections.length; ++i) {
				if (annotation != null) {
					context.addResolution(new TclCheckerAnnotationResolution(corrections[i], context.getEditor(),
							context.getModule(), marker), annotation);
				} else {
					context.addResolution(new TclCheckerMarkerResolution(corrections[i]), marker);
				}
			}
		}
	}
}
