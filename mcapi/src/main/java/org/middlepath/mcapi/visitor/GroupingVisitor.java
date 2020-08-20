package org.middlepath.mcapi.visitor;

import java.util.Collection;
import org.middlepath.mcapi.block.Element;
import org.middlepath.mcapi.group.Grouping;

/**
 * A composite element visitor that can visit over a Grouping of elements, executing each visitor
 * for each element in the group.
 * 
 * @author DAB
 *
 * @param <T> An Element type
 */
public class GroupingVisitor<T extends Element> extends CompositeElementVisitor<T> {

	public GroupingVisitor(Visitor<T> visitor) {
		super(visitor);
	}
	
	public GroupingVisitor(Collection<Visitor<T>> visitors) {
		super(visitors);
	}
	
	public void visit(Grouping<T> grouping) {
		grouping.forEach(e ->
				childVisitors.forEach(v -> v.visit(e)));
	}
}
