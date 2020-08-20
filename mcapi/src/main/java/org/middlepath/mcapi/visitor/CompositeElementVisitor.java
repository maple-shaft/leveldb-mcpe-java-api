package org.middlepath.mcapi.visitor;

import java.util.Collection;
import java.util.LinkedList;
import org.middlepath.mcapi.block.Element;

/**
 * A composition of element visitors.
 * 
 * @author DAB
 *
 * @param <T> An Element type
 */
public class CompositeElementVisitor<T extends Element> implements Visitor<T> {

	protected LinkedList<Visitor<T>> childVisitors = new LinkedList<Visitor<T>>();
	
	public CompositeElementVisitor(Visitor<T> visitor) {
		this.childVisitors.add(visitor);
	}
	
	public CompositeElementVisitor(Collection<Visitor<T>> visitors) {
		this.childVisitors.addAll(visitors);
	}
	
	@Override
	public void visit(T t) {
		this.childVisitors.forEach(v -> v.visit(t));
	}

}
