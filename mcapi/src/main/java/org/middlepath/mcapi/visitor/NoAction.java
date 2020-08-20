package org.middlepath.mcapi.visitor;

import org.middlepath.mcapi.generic.Locatable;

public class NoAction<T extends Locatable> implements VisitAction<T> {

	@Override
	public void perform(T t) {
		// noop
	}
	
}
