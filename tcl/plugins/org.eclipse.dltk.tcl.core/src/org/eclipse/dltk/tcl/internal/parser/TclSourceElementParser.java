/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *     xored software, Inc. - todo task parser added (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParserConstants;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.compiler.task.ITaskReporter;
import org.eclipse.dltk.compiler.task.TodoTaskPreferences;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceElementParserExtension;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.eclipse.dltk.tcl.core.TclParseUtil.CodeModel;

public class TclSourceElementParser extends AbstractSourceElementParser
		implements ISourceElementParserExtension {

	private IScriptProject scriptProject = null;

	/*
	 * @see org.eclipse.dltk.core.AbstractSourceElementParser#createVisitor()
	 */
	protected SourceElementRequestVisitor createVisitor() {
		return new TclSourceElementRequestVisitor(this.getRequestor(), this
				.getProblemReporter());
	}

	public void parseSourceModule(char[] contents, ISourceModuleInfo astCache,
			char[] filename) {

		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(filename, contents, getNatureId(),
						getProblemReporter(), astCache,
						ISourceParserConstants.DEFAULT);
		//

		TclSourceElementRequestVisitor requestor = (TclSourceElementRequestVisitor) createVisitor();
		requestor.setScriptProject(null);
		if (getProblemReporter() != null) {
			if (this.scriptProject != null) {
				boolean markersCleaned = getProblemReporter()
						.isMarkersCleaned();
				if (markersCleaned) {
					CodeModel model = new CodeModel(new String(contents));
					requestor.setCodeModel(model);
					requestor.setScriptProject(this.scriptProject);
				}
			}
		}
		try {
			moduleDeclaration.traverse(requestor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (getProblemReporter() != null) {
			final ITaskReporter taskReporter = (ITaskReporter) getProblemReporter()
					.getAdapter(ITaskReporter.class);
			if (taskReporter != null) {
				taskReporter.clearTasks();
				parseTasks(taskReporter, contents, moduleDeclaration);
			}
		}
	}

	protected void parseTasks(ITaskReporter taskReporter, char[] content,
			ModuleDeclaration moduleDeclaration) {
		final TodoTaskPreferences preferences = new TodoTaskPreferences(
				TclPlugin.getDefault().getPluginPreferences());
		if (preferences.isEnabled()) {
			final TclTodoTaskAstParser taskParser = new TclTodoTaskAstParser(
					taskReporter, preferences, moduleDeclaration);
			if (taskParser.isValid()) {
				taskParser.parse(content);
			}
		}
	}

	/*
	 * @see org.eclipse.dltk.core.AbstractSourceElementParser#getNatureId()
	 */
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

	public void setScriptProject(IScriptProject project) {
		this.scriptProject = project;
	}
}
