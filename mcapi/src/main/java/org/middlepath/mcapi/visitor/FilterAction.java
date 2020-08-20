package org.middlepath.mcapi.visitor;

import org.middlepath.mcapi.generic.Locatable;

/**
 * An action that can be taken by a Visitor to filter elements of type T in a container.
 * 
 * @author DAB
 *
 * @param <T> A type of Locatable that can be filtered
 */
public interface FilterAction<T extends Locatable> {

	/**
	 * 
	 * @param t A Locatable instance
	 * @return True if the item should be filtered out
	 */
	public boolean filter(T t);
}
