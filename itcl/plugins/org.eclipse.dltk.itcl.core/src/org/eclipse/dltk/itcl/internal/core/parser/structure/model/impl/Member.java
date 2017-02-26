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

import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMember;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IRange;
import org.eclipse.dltk.tcl.ast.Node;

public abstract class Member implements IMember {

	private String name;
	private Visibility visibility = Visibility.PRIVATE;
	private int nameStart;
	private int nameEnd;
	private int start;
	private int end;

	@Override
	public int getNameStart() {
		return nameStart;
	}

	@Override
	public void setNameStart(int nameStart) {
		this.nameStart = nameStart;
	}

	@Override
	public int getNameEnd() {
		return nameEnd;
	}

	@Override
	public void setNameEnd(int nameEnd) {
		this.nameEnd = nameEnd;
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	@Override
	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public void setNameRange(Node node) {
		setNameStart(node.getStart());
		setNameEnd(node.getEnd() - 1);
	}

	@Override
	public void setRange(Node node) {
		setStart(node.getStart());
		setEnd(node.getEnd());
	}

	@Override
	public void setRange(IRange range) {
		setStart(range.getStart());
		setEnd(range.getEnd());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

}
