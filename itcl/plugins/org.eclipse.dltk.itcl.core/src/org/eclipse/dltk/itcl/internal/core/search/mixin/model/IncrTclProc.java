package org.eclipse.dltk.itcl.internal.core.search.mixin.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.core.search.mixin.model.TclMixinElement;

public class IncrTclProc extends TclMixinElement implements IIncrTclMixinConstants {
	@Override
	public int getType() {
		return ELEMENT_INCRTCL_PROC;
	}

	@Override
	public String toString() {
		return "incrtclproc";
	}

	@Override
	protected boolean isValidModelElement(IModelElement element) {
		return element.getElementType() == IModelElement.METHOD;
	}
}
