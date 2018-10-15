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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.ast;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.dltk.tcl.ast.StringArgument#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.dltk.tcl.ast.StringArgument#getRawValue <em>Raw Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.dltk.tcl.ast.AstPackage#getStringArgument()
 * @model
 * @generated
 */
public interface StringArgument extends TclArgument {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.dltk.tcl.ast.AstPackage#getStringArgument_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.dltk.tcl.ast.StringArgument#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Raw Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Raw Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Raw Value</em>' attribute.
	 * @see #setRawValue(String)
	 * @see org.eclipse.dltk.tcl.ast.AstPackage#getStringArgument_RawValue()
	 * @model transient="true"
	 * @generated
	 */
	String getRawValue();

	/**
	 * Sets the value of the '{@link org.eclipse.dltk.tcl.ast.StringArgument#getRawValue <em>Raw Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raw Value</em>' attribute.
	 * @see #getRawValue()
	 * @generated
	 */
	void setRawValue(String value);

} // StringArgument
