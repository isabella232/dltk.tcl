/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser;

import java.util.ArrayList;
import java.util.List;

public class TclErrorCollector implements ITclErrorReporter {
	private List<TclError> errors = new ArrayList<TclError>();

	public void report(int code, String message, String[] extraMessage,
			int start, int end, int kind) {
		errors.add(new TclError(code, message, extraMessage, start, end, kind));
	}

	public void addAll(TclErrorCollector collector) {
		for (TclError e : collector.errors) {
			report(e.getCode(), e.getMessage(), e.getExtraArguments(), e
					.getStart(), e.getEnd(), e.getErrorKind());
		}
	}

	public void reportAll(ITclErrorReporter reporter) {
		if (reporter != null) {
			for (TclError error : this.errors) {
				reporter.report(error.getCode(), error.getMessage(), error
						.getExtraArguments(), error.getStart(), error.getEnd(),
						error.getErrorKind());
			}
		}
	}

	public int getCount() {
		return this.errors.size();
	}

	public TclError[] getErrors() {
		return this.errors.toArray(new TclError[this.errors.size()]);
	}
}
