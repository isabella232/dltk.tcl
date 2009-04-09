package org.eclipse.dltk.tcl.internal.core.packages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.internal.core.MementoModelElementUtil;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.OpenableElementInfo;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.internal.core.util.MementoTokenizer;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterContainerHelper;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.internal.core.packages.PackagesManager.PackageInformation;
import org.eclipse.dltk.utils.CorePrinter;

public class PackagesFragment extends Openable implements IProjectFragment {
	public static final IPath PATH = new Path(IBuildpathEntry.BUILDPATH_SPECIAL
			+ "@packages@");

	protected PackagesFragment(ScriptProject project) {
		super(project);
	}

	public String getElementName() {
		return "@packages@";
	}

	protected boolean buildStructure(OpenableElementInfo info,
			IProgressMonitor pm, Map newElements, IResource underlyingResource)
			throws ModelException {
		IScriptProject project = getScriptProject();
		IInterpreterInstall install = null;
		try {
			install = ScriptRuntime.getInterpreterInstall(project);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (install == null) {
			return false;
		}
		Set packages = InterpreterContainerHelper
				.getInterpreterContainerDependencies(project);
		List children = new ArrayList();
		if (packages != null) {
			String names[] = (String[]) packages.toArray(new String[packages
					.size()]);
			for (int i = 0; i < names.length; i++) {
				PackageInformation pkgInfo = PackagesManager.getInstance()
						.getPacakgeInfo(names[i], install);
				if (pkgInfo != null) {
					children.add(new PackageElement(this, names[i], ""));
				}
			}
		}
		info.setChildren((IModelElement[]) children
				.toArray(new IModelElement[children.size()]));
		return true;
	}

	public Object createElementInfo() {
		return new OpenableElementInfo();
	}

	public IModelElement getHandleFromMemento(String token,
			MementoTokenizer memento, WorkingCopyOwner owner) {
		switch (token.charAt(0)) {
		case JEM_SCRIPTFOLDER:
			if (!memento.hasMoreTokens())
				return this;
			String classFileName = memento.nextToken();
			ModelElement classFile = (ModelElement) getScriptFolder(classFileName);
			return classFile.getHandleFromMemento(memento, owner);
		case JEM_USER_ELEMENT:
			return MementoModelElementUtil.getHandleFromMememento(memento,
					this, owner);
		}
		return null;
	}

	protected char getHandleMementoDelimiter() {
		return JEM_USER_ELEMENT;
	}

	public void printNode(CorePrinter output) {
	}

	public void copy(IPath destination, int updateResourceFlags,
			int updateModelFlags, IBuildpathEntry sibling,
			IProgressMonitor monitor) throws ModelException {
	}

	public IScriptFolder createScriptFolder(String name, boolean force,
			IProgressMonitor monitor) throws ModelException {
		return null;
	}

	public void delete(int updateResourceFlags, int updateModelFlags,
			IProgressMonitor monitor) throws ModelException {
	}

	public Object[] getForeignResources() throws ModelException {
		return new Object[0];
	}

	public int getKind() throws ModelException {
		return IProjectFragment.K_SOURCE;
	}

	public IBuildpathEntry getRawBuildpathEntry() throws ModelException {
		IScriptProject project = getScriptProject();
		IBuildpathEntry[] rawBuildpath = project.getRawBuildpath();
		for (int i = 0; i < rawBuildpath.length; i++) {
			if (rawBuildpath[i].getPath().segment(0).equals(
					ScriptRuntime.INTERPRETER_CONTAINER)) {
				return rawBuildpath[i];
			}
		}
		return null;
	}

	public IScriptFolder getScriptFolder(IPath path) {
		return null;
	}

	public IScriptFolder getScriptFolder(String path) {
		return null;
	}

	public boolean isArchive() {
		return false;
	}

	public boolean isBuiltin() {
		return false;
	}

	public boolean exists() {
		return true;
	}

	public boolean isExternal() {
		return true;
	}

	public void move(IPath destination, int updateResourceFlags,
			int updateModelFlags, IBuildpathEntry sibling,
			IProgressMonitor monitor) throws ModelException {
	}

	public int getElementType() {
		return IModelElement.PROJECT_FRAGMENT;
	}

	public IPath getPath() {
		return PATH;
	}

	public IResource getResource() {
		return null;
	}
}
