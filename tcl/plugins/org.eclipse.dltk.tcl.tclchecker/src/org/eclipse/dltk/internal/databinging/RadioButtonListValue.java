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
package org.eclipse.dltk.internal.databinging;

import java.util.Map;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;

public class RadioButtonListValue<V> extends AbstractObservableValue {

	private final Class<V> valueType;
	private final Map<Button, V> buttons;
	private V selectionValue;

	private Listener updateListener = event -> {
		V oldSelectionValue = selectionValue;
		selectionValue = getSelection();
		notifyIfChanged(oldSelectionValue, selectionValue);
	};

	private Listener disposeListener = e -> RadioButtonListValue.this.dispose();

	private V getSelection() {
		for (Map.Entry<Button, V> entry : buttons.entrySet()) {
			if (entry.getKey().getSelection()) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * @param button
	 */
	public RadioButtonListValue(Class<V> valueType, Map<Button, V> buttons) {
		super(DisplayRealm.getRealm(buttons.keySet().iterator().next().getDisplay()));
		this.valueType = valueType;
		this.buttons = buttons;
		init();
	}

	private void init() {
		for (Button button : buttons.keySet()) {
			button.addListener(SWT.Selection, updateListener);
			button.addListener(SWT.DefaultSelection, updateListener);
			button.addListener(SWT.Dispose, disposeListener);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doSetValue(final Object value) {
		V oldSelectionValue = selectionValue;
		selectionValue = (V) value;
		boolean buttonUpdated = false;
		for (Map.Entry<Button, V> entry : buttons.entrySet()) {
			if (entry.getValue() == selectionValue) {
				entry.getKey().setSelection(true);
				buttonUpdated = true;
				break;
			}
		}
		if (!buttonUpdated) {
			for (Button button : buttons.keySet()) {
				button.setSelection(false);
			}
		}
		notifyIfChanged(oldSelectionValue, selectionValue);
	}

	@Override
	public Object doGetValue() {
		return getSelection();
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	@Override
	public synchronized void dispose() {
		super.dispose();
		for (Button button : buttons.keySet()) {
			if (!button.isDisposed()) {
				button.removeListener(SWT.Selection, updateListener);
				button.removeListener(SWT.DefaultSelection, updateListener);
			}
		}
	}

	/**
	 * Notifies consumers with a value change event only if a change occurred.
	 *
	 * @param oldValue
	 * @param newValue
	 */
	private void notifyIfChanged(V oldValue, V newValue) {
		if (oldValue != newValue) {
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	}
}
