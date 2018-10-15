/*******************************************************************************
 * Copyright (c) 2016, 2017 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IWatchExpressionListener;
import org.eclipse.debug.core.model.IWatchExpressionResult;
import org.eclipse.dltk.debug.core.eval.IScriptEvaluationCommand;
import org.eclipse.dltk.debug.core.eval.IScriptEvaluationResult;
import org.eclipse.dltk.debug.core.model.IScriptThread;
import org.eclipse.dltk.debug.core.model.IScriptValue;
import org.eclipse.dltk.internal.debug.core.model.ScriptValueProxy;
import org.eclipse.dltk.internal.debug.core.model.ScriptWatchExpressionDelegate;

public class TclWatchExpressionDelegate extends ScriptWatchExpressionDelegate {

	@Override
	protected String prepareExpression(String expression) {
		if (expression.startsWith("$")) { //$NON-NLS-1$
			return "expr { " + expression + " }"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return expression;
	}

	private static final class TclWatchExpressionValueProxy extends ScriptValueProxy {
		private final String expression;

		private TclWatchExpressionValueProxy(IScriptValue origin, String expression) {
			super(origin);
			this.expression = expression;
		}

		@Override
		public String getEvalName() {
			return expression;
		}

		@Override
		public IScriptEvaluationCommand createEvaluationCommand(String messageTemplate, IScriptThread thread) {
			return null;
		}
	}

	private static class TclWatchExpressionResult implements IWatchExpressionResult {

		private final String[] errorMessages;
		private final DebugException exception;
		private final String expressionText;
		private final IValue value;
		private final boolean errors;

		/**
		 * @param errorMessages
		 * @param exception
		 * @param expressionText
		 * @param value
		 * @param errors
		 */
		public TclWatchExpressionResult(String[] errorMessages, DebugException exception, String expressionText,
				IValue value, boolean errors) {
			this.errorMessages = errorMessages;
			this.errors = errors;
			this.exception = exception;
			this.expressionText = expressionText;
			this.value = value;
		}

		@Override
		public String[] getErrorMessages() {
			return errorMessages;
		}

		@Override
		public DebugException getException() {
			return exception;
		}

		@Override
		public String getExpressionText() {
			return expressionText;
		}

		@Override
		public IValue getValue() {
			return value;
		}

		@Override
		public boolean hasErrors() {
			return errors;
		}

	}

	private static class TclListenerAdapter extends ListenerAdpater {

		private final String expression;

		public TclListenerAdapter(IWatchExpressionListener listener, String expression) {
			super(listener);
			this.expression = expression;
		}

		@Override
		public void evaluationComplete(IScriptEvaluationResult result) {
			if (result != null && result.getValue() != null) {
				listener.watchEvaluationFinished(
						new TclWatchExpressionResult(result.getErrorMessages(), result.getException(), expression,
								new TclWatchExpressionValueProxy(result.getValue(), expression), result.hasErrors()));
			} else {
				super.evaluationComplete(result);
			}
		}

	}

	@Override
	protected ListenerAdpater createListener(IWatchExpressionListener listener, String expression) {
		return new TclListenerAdapter(listener, expression);
	}

}
