package org.eclipse.dltk.itcl.internal.core.parser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;

/**
 *
 * ensemble ensName ?command arg arg...? or ensemble ensName {
 *
 * part partName args body
 *
 * ...
 *
 * ensemble partName {
 *
 * part subPartName args body
 *
 * part subPartName args body
 *
 * ... } }
 *
 */
public class IncrTclEnsemble extends Declaration {
	private List<IncrTclEnsemble> ensembles;
	private List<IncrTclEnsemblePart> parts;

	public IncrTclEnsemble(int start, int end) {
		super(start, end);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (this.ensembles != null) {
				Iterator<IncrTclEnsemble> it = this.ensembles.iterator();
				while (it.hasNext()) {
					IncrTclEnsemble arg = it.next();
					arg.traverse(visitor);
				}
			}
			if (this.parts != null) {
				Iterator<IncrTclEnsemblePart> it = this.parts.iterator();
				while (it.hasNext()) {
					IncrTclEnsemblePart arg = it.next();
					arg.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public void addEnsamble(IncrTclEnsemble part) {
		initialize();
		this.ensembles.add(part);
	}

	private void initialize() {
		if (this.ensembles == null) {
			this.ensembles = new ArrayList<>();
		}
		if (this.parts == null) {
			this.parts = new ArrayList<>();
		}
	}

	public List<IncrTclEnsemble> getEnsembles() {
		initialize();
		return ensembles;
	}

	public void setEnsembles(List<IncrTclEnsemble> ensembles) {
		this.ensembles = ensembles;
		initialize();
	}

	public List<IncrTclEnsemblePart> getParts() {
		initialize();
		return parts;
	}

	public void setParts(List<IncrTclEnsemblePart> parts) {
		this.parts = parts;
		initialize();
	}

	public void addPart(IncrTclEnsemblePart part) {
		initialize();
		this.parts.add(part);
	}
}
