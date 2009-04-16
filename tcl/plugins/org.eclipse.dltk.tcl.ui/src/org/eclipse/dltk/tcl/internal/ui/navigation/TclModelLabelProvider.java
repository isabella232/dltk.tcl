package org.eclipse.dltk.tcl.internal.ui.navigation;

import org.eclipse.dltk.tcl.internal.core.packages.TclPackageElement;
import org.eclipse.dltk.tcl.internal.core.packages.TclPackageSourceModule;
import org.eclipse.dltk.tcl.internal.core.packages.TclPackagesFragment;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TclModelLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if (element instanceof TclPackagesFragment) {
			return "Packages";
		} else if (element instanceof TclPackageElement) {
			TclPackageElement pkg = (TclPackageElement) element;
			String result = pkg.getPackageName();
			if (pkg.getVersion() != null) {
				result += " " + pkg.getVersion();
			}
			return result;
		} else if (element instanceof TclPackageSourceModule) {
			return ((TclPackageSourceModule) element).getElementName();
		}
		return null;
	}

	public Image getImage(Object element) {
		return null;
	}
}
