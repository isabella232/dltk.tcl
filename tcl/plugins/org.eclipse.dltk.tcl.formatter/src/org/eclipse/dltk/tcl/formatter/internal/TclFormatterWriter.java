/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/
package org.eclipse.dltk.tcl.formatter.internal;

import org.eclipse.dltk.formatter.FormatterWriter;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterIndentGenerator;

public class TclFormatterWriter extends FormatterWriter {

	/**
	 * @param document
	 * @param lineDelimiter
	 * @param indentGenerator
	 */
	public TclFormatterWriter(IFormatterDocument document,
			String lineDelimiter, IFormatterIndentGenerator indentGenerator) {
		super(document, lineDelimiter, indentGenerator);
	}

}
