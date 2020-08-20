package org.middlepath.mcapi.visitor;

import java.util.Collection;
import java.util.LinkedList;
import org.middlepath.mcapi.block.Element;

public class FilteringElementTransformingVisitor<T extends Element> extends ElementTransformingVisitor<T> {

	private LinkedList<FilterAction<T>> filters = new LinkedList<FilterAction<T>>();
	
	private LinkedList<T> filterResultCache = new LinkedList<T>();
	
	public FilteringElementTransformingVisitor() {
		super();
	}
	
	public FilteringElementTransformingVisitor(VisitAction<T> action, FilterAction<T> filter) {
		super(action);
		this.filters.add(filter);
	}
	
	public FilteringElementTransformingVisitor(VisitAction<T> action, Collection<FilterAction<T>> filters) {
		super(action);
		this.filters.addAll(filters);
	}
	
	@Override
	/**
	 * Run all filters for t and if it is not filtered then perform the action.
	 */
	public void visit(T t) {
		if (this.filters.stream().allMatch(a -> !a.filter(t))) {
			this.actions.forEach(a -> a.perform(t));
			this.filterResultCache.add(t);
		}
	}
	
	public LinkedList<T> getFilterResultCache() {
		return filterResultCache;
	}
}
