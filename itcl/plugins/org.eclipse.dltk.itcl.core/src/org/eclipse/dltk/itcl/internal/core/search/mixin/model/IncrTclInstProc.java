package org.eclipse.dltk.itcl.internal.core.search.mixin.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.core.search.mixin.model.TclMixinElement;

public class IncrTclInstProc extends TclMixinElement implements IIncrTclMixinConstants {
	@Override
	public int getType() {
		return ELEMENT_INCRTCL_INSTPROC;
	}

	@Override
	public String toString() {
		return "incrtclinstproc";
	}

	@Override
	protected boolean isValidModelElement(IModelElement element) {
		return element.getElementType() == IModelElement.METHOD;
	}
}
