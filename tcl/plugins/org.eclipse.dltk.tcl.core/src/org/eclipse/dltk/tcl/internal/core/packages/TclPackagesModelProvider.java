package org.eclipse.dltk.tcl.internal.core.packages;

import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelProvider;
import org.eclipse.dltk.internal.core.ScriptProject;

public class TclPackagesModelProvider implements IModelProvider {
	public TclPackagesModelProvider() {
	}

	public void provideModelChanges(IModelElement parentElement, List children) {
		if (parentElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
			TclPackageFragment fragment = new TclPackageFragment(
					(ScriptProject) parentElement);
			if (!children.contains(fragment)) {
				children.add(fragment);
			}
		}
	}

	public boolean isModelChangesProvidedFor(IModelElement modelElement,
			String name) {
		if (modelElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
			return true;
		}
		return false;
	}
}
