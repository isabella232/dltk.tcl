/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.itcl.internal.core.parser.structure.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.itcl.internal.core.IIncrTclModifiers;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMethod;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.structure.AbstractTclCommandModelBuilder.Parameter;

public class Method extends Member implements IMethod {

	private MethodKind kind = MethodKind.METHOD;
	private final List<Parameter> parameters = new ArrayList<>();
	private List<TclArgument> bodies = null;

	@Override
	public MethodKind getKind() {
		return kind;
	}

	@Override
	public void setKind(MethodKind kind) {
		this.kind = kind;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	@Override
	public List<TclArgument> getBodies() {
		return bodies != null ? bodies : Collections.<TclArgument>emptyList();
	}

	@Override
	public void addBody(TclArgument body) {
		if (bodies == null) {
			bodies = new ArrayList<>();
		}
		bodies.add(body);
	}

	@Override
	public int getModifiers() {
		int modifiers = IIncrTclModifiers.AccIncrTcl | getVisibility().getModifiers() | getKind().getModifiers();
		if (getKind().isMaskVisibility()) {
			modifiers &= ~(Modifiers.AccPublic | Modifiers.AccProtected | Modifiers.AccPrivate);
		}
		return modifiers;
	}

}
