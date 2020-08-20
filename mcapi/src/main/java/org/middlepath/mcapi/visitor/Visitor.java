package org.middlepath.mcapi.visitor;

import org.middlepath.mcapi.generic.Locatable;

/**
 * Defines the contract for a Visitor implementation that can be used to visit types of Locatable.
 * 
 * @author dustin
 *
 * @param <T> A Locatable type
 */
public interface Visitor<T extends Locatable> {

	public void visit(T t);
}
