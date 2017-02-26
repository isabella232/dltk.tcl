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
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IClass;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMember;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMember.Visibility;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMethod;

public class ClassImpl implements IClass {

	private final List<String> superclasses = new ArrayList<>();
	private final List<IMember> members = new ArrayList<>();
	private final Stack<Visibility> visibilities = new Stack<>();
	private String name;

	@Override
	public void addMember(IMember member) {
		members.add(member);
	}

	@Override
	public void addSuperclass(String name) {
		superclasses.add(name);
	}

	@Override
	public Visibility peekVisibility() {
		return visibilities.isEmpty() ? Visibility.PRIVATE : visibilities.peek();
	}

	@Override
	public Visibility popVisibility() {
		return visibilities.pop();
	}

	@Override
	public void pushVisibility(Visibility visibility) {
		visibilities.push(visibility);
	}

	@Override
	public String[] getSuperClasses() {
		return superclasses.toArray(new String[superclasses.size()]);
	}

	@Override
	public List<IMember> getMembers() {
		return members;
	}

	@Override
	public IMethod findMethod(String name) {
		for (IMember member : members) {
			if (member instanceof IMethod && name.equals(member.getName())) {
				return (IMethod) member;
			}
		}
		return null;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param namespace
	 */
	public void setName(String name) {
		this.name = name;
	}

}
