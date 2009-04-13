package org.eclipse.dltk.tcl.internal.core.packages;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelProvider;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.ScriptProject;

public class TclPackagesModelProvider implements IModelProvider {

	public TclPackagesModelProvider() {
	}

	public void buildStructure(IModelElement parentElement, List children) {
		if (parentElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
		}
	}

	public boolean providesFor(IModelElement modelElement, IPath path) {
		if (modelElement.getElementType() == IModelElement.SCRIPT_PROJECT) {
			return true;
		}
		return false;
	}

	public IProjectFragment getProjectFragment(IPath entryPath,
			IScriptProject project) {
		if (entryPath.segment(0).equals(TclPackagesFragment.PATH.toString())) {
			return new TclPackagesFragment((ScriptProject) project);
		}
		return null;
	}

}
