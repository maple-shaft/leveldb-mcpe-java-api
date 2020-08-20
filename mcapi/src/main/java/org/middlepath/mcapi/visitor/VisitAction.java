package org.middlepath.mcapi.visitor;

import org.middlepath.mcapi.generic.Locatable;

/**
 * Defines an Action that can be performed on a Locatable object that has been visited.
 * 
 * @author DAB
 *
 * @param <T> A Locatable type
 */
public interface VisitAction<T extends Locatable> {

	public abstract void perform(T t);
}
