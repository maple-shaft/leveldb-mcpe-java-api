package org.middlepath.mcapi.visitor;

import java.util.Collection;
import java.util.LinkedList;

import org.middlepath.mcapi.block.Element;

/**
 * An Element visitor that can perform VisitAction with capabilities to change the underlying element object.
 * 
 * @author DAB
 *
 * @param <T> An Element type
 */
public class ElementTransformingVisitor<T extends Element> implements Visitor<T> {

	protected LinkedList<VisitAction<T>> actions = new LinkedList<VisitAction<T>>();
	
	public ElementTransformingVisitor() {
		this(new NoAction<T>());
	}
	
	public ElementTransformingVisitor(VisitAction<T> action) {
		actions.add(action);
	}
	
	public ElementTransformingVisitor(Collection<VisitAction<T>> actions) {
		actions.addAll(actions);
	}
	
	@Override
	public void visit(T t) {
		actions.forEach(a -> a.perform(t));
	}

}
