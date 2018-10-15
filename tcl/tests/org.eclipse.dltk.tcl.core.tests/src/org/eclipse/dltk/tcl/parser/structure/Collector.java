/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.parser.structure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.compiler.ISourceElementRequestorExtension;
import org.eclipse.dltk.compiler.SourceElementRequestorAdaptor;
import org.eclipse.dltk.compiler.SourceElementRequestorMode;
import org.eclipse.dltk.core.caching.StructureModelCollector;

public class Collector extends StructureModelCollector
		implements ISourceElementRequestorExtension {

	/**
	 * @param requestor
	 */
	public Collector() {
		super(new SourceElementRequestorAdaptor());
	}

	static class Tag {
		final int tag;
		final int offset;
		String context;

		public Tag(int tag, int offset) {
			this.tag = tag;
			this.offset = offset;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Tag) {
				final Tag other = (Tag) obj;
				return tag == other.tag && offset == other.offset;
			}
			return false;
		}

		@Override
		public String toString() {
			return tag + "@" + offset + (context != null ? "=" + context : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	final List<Tag> tags = new ArrayList<>();

	@Override
	protected void writeTag(int tag) throws IOException {
		tags.add(new Tag(tag, out.size()));
		super.writeTag(tag);
	}

	@Override
	public byte[] getBytes() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			saveDataTo(stream);
		} catch (IOException e) {
			// should not happen
		}
		return stream.toByteArray();
	}

	@Override
	public boolean enterFieldCheckDuplicates(FieldInfo info) {
		final boolean result = super.enterFieldCheckDuplicates(info);
		getLastTag().context = info.name;
		return result;
	}

	@Override
	public void enterMethod(MethodInfo info) {
		super.enterMethod(info);
		getLastTag().context = info.name;
	}

	@Override
	public void enterMethodRemoveSame(MethodInfo info) {
		super.enterMethodRemoveSame(info);
		getLastTag().context = info.name;
	}

	@Override
	public void enterType(TypeInfo info) {
		super.enterType(info);
		getLastTag().context = info.name;
	}

	@Override
	public boolean enterTypeAppend(String fullName, String delimiter) {
		final boolean result = super.enterTypeAppend(fullName, delimiter);
		getLastTag().context = fullName;
		return result;
	}

	/**
	 * @return
	 */
	private Tag getLastTag() {
		return tags.get(tags.size() - 1);
	}

	@Override
	public void acceptFieldReference(String fieldName, int sourcePosition) {
		// ignore
	}

	@Override
	public void acceptMethodReference(String methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
		// ignore
	}

	@Override
	public void acceptTypeReference(String typeName, int sourcePosition) {
		// ignore
	}

	@Override
	public SourceElementRequestorMode getMode() {
		return SourceElementRequestorMode.STRUCTURE;
	}

}
