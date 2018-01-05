/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConfigsSwitch.java,v 1.4 2009/02/27 09:16:02 apanchenk Exp $
 */
package org.eclipse.dltk.tcl.tclchecker.model.configs.util;

import java.util.Map;

import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerConfig;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerEnvironmentInstance;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance;
import org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigsPackage;
import org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState;
import org.eclipse.dltk.validators.configs.ValidatorConfig;
import org.eclipse.dltk.validators.configs.ValidatorEnvironmentInstance;
import org.eclipse.dltk.validators.configs.ValidatorInstance;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigsPackage
 * @generated
 */
public class ConfigsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConfigsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigsSwitch() {
		if (modelPackage == null) {
			modelPackage = ConfigsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ConfigsPackage.CHECKER_CONFIG: {
				CheckerConfig checkerConfig = (CheckerConfig)theEObject;
				T result = caseCheckerConfig(checkerConfig);
				if (result == null) result = caseValidatorConfig(checkerConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigsPackage.MESSAGE_STATE_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<String, MessageState> messageStateMap = (Map.Entry<String, MessageState>)theEObject;
				T result = caseMessageStateMap(messageStateMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigsPackage.CHECKER_ENVIRONMENT_INSTANCE: {
				CheckerEnvironmentInstance checkerEnvironmentInstance = (CheckerEnvironmentInstance)theEObject;
				T result = caseCheckerEnvironmentInstance(checkerEnvironmentInstance);
				if (result == null) result = caseValidatorEnvironmentInstance(checkerEnvironmentInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigsPackage.CHECKER_INSTANCE: {
				CheckerInstance checkerInstance = (CheckerInstance)theEObject;
				T result = caseCheckerInstance(checkerInstance);
				if (result == null) result = caseValidatorInstance(checkerInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Checker Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Checker Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCheckerConfig(CheckerConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Message State Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Message State Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMessageStateMap(Map.Entry<String, MessageState> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Checker Environment Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Checker Environment Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCheckerEnvironmentInstance(CheckerEnvironmentInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Checker Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Checker Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCheckerInstance(CheckerInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Validator Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Validator Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValidatorInstance(ValidatorInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Validator Environment Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Validator Environment Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValidatorEnvironmentInstance(ValidatorEnvironmentInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Validator Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Validator Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValidatorConfig(ValidatorConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ConfigsSwitch
