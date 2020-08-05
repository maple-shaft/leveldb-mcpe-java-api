package org.middlepath.mcapi.source;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.middlepath.mcapi.block.Element;
import org.middlepath.mcapi.generic.Coordinate;

/**
 * <p>A Locatable source that combines multiple LocatableSources as a single source. This is useful
 * for example as a unified source of all subchunks in a CompleteChunk.</p>
 *
 * @author DAB
 */
public class CompositeLocatableSource<T extends Element> implements LocatableSource<T> {

	private Set<LocatableSource<T>> children = new HashSet<LocatableSource<T>>();
	private Class<T> classType;
	
	public CompositeLocatableSource(Class<T> classType) {
		this.classType = classType;
	}
	
	public CompositeLocatableSource(Class<T> classType, LocatableSource<T>... sources) {
		this(classType, Arrays.asList(sources));
	}
	
	public CompositeLocatableSource(Class<T> classType, Collection<LocatableSource<T>> locatableSources) {
		this.children.addAll(locatableSources);
		this.classType = classType;
	}
	
	public void add(LocatableSource<T> locatableSource) {
		this.children.add(locatableSource);
	}
	
	public Class<T> getBlockClass() {
		return this.classType;
	}
	
	@Override
	public T getLocatable(Coordinate c) {
		T ret = null;
		for (LocatableSource<T> source : children) {
			ret = source.getLocatable(c);
			if (ret != null)
				break;
		}
		return ret;
	}
	
}