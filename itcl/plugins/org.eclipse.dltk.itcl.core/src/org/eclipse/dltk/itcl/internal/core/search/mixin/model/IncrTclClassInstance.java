package org.eclipse.dltk.itcl.internal.core.search.mixin.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.core.search.mixin.model.TclMixinElement;

public class IncrTclClassInstance extends TclMixinElement implements IIncrTclMixinConstants {
	@Override
	public int getType() {
		return ELEMENT_INCRTCL_CLASS_INSTANCE;
	}

	@Override
	public String toString() {
		return "incr_class_instance";
	}

	@Override
	protected boolean isValidModelElement(IModelElement element) {
		return element.getElementType() == IModelElement.FIELD;
	}
}
