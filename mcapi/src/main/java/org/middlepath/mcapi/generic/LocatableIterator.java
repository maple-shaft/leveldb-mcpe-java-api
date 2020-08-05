package org.middlepath.mcapi.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.middlepath.mcapi.source.LocatableSource;

/**
 * <p>A generic Locatable Iterator that wraps a Coordinate iterator with a Locatable Source for fetching items.</p>
 *
 * @author DAB
 */
public class LocatableIterator <T extends Locatable> implements Iterator<T> {

	protected final LocatableSource<T> source;
	protected final Iterator<Coordinate> coordinateIterator;
	
	protected T prefetch = null;
	
	public LocatableIterator(Iterator<Coordinate> coordinateIterator, LocatableSource<T> source) {
		this.source = source;
		this.coordinateIterator = coordinateIterator;
	}
	
	@Override
	/**
	 * Finding a null will not stop the iterator, it will keep going as long as there are more coordinates.
	 */
	public boolean hasNext() {
		while (prefetch == null) {
			boolean hasCoord = coordinateIterator.hasNext();
			if (!hasCoord)
				return false;
			prefetch = fetch();
		}
		return true;
	}
	
	private T fetch() {
		Coordinate c = coordinateIterator.next();
		try {
			return source.getLocatable(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException("No more records");
		T ret = prefetch;
		prefetch = null;
		return ret;
	}
	
}