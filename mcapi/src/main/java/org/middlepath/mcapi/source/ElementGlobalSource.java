package org.middlepath.mcapi.source;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.middlepath.mcapi.block.Element;
import org.middlepath.mcapi.generic.Coordinate;

/**
 * <p>A universal locatable source has a collection of sources and can provide any Element.</p>
 *
 * @author DAB
 */
public class ElementGlobalSource implements LocatableSource<Element> {

	private Map<Class<? extends Element>,
			LocatableSource<? extends Element>> sources =
			new HashMap<Class<? extends Element>,
					LocatableSource<? extends Element>>();
					
	public ElementGlobalSource() {
	
	}
	
	public void add(Class<? extends Element> key,
			LocatableSource<? extends Element> value) {
		sources.put(key, value);
	}
	
	@Override
	/**
	 * Loop through all sources and return the first one... i guess this is ok
	 */
	public Element getLocatable(Coordinate c) {
		return sources.values().stream().map(e -> e.getLocatable(c))
				.filter(Objects::nonNull)
				.findAny().orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	public <R extends Element> R getLocatableByType(
			Class<R> clazz, Coordinate c) {
		return (R) sources.get(clazz).getLocatable(c);
	}
	
}