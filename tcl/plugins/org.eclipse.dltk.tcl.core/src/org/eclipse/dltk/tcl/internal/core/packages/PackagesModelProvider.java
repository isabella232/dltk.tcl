package org.eclipse.dltk.tcl.internal.core.packages;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelProvider;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.ScriptProject;

public class PackagesModelProvider implements IModelProvider {

	public PackagesModelProvider() {
	}

	public void buildStructure(IModelElement parentElement, List children) {
		if (parentElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
			// Just add packages to project from list of all project packages.
		//	children.add(new PackagesFragment((ScriptProject) parentElement));
		}
		System.out.println(parentElement + ":" + children);
	}

	public boolean providesFor(IModelElement modelElement, IPath path) {
		if (modelElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
			return true;
		}
		return false;
	}

	public IProjectFragment getProjectFragment(IPath entryPath,
			IScriptProject project) {
		if (entryPath.equals(PackagesFragment.PATH)) {
			return new PackagesFragment((ScriptProject) project);
		}
		return null;
	}

}
