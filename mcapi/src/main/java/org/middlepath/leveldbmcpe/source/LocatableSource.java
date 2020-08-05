package org.middlepath.leveldbmcpe.source;

import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.Locatable;

/**
 * <p>Provides a source for a type of Locatable object.  Implementers of this may be looking 
 * in block storage records for block data, or may be fetching locatable chunks from the 
 * database files.</p>
 *
 * @author DAB
 * 
 * @param <T> Any Locatable class
 */
public interface LocatableSource<T extends Locatable> {

	public T getLocatable(Coordinate c);
	
}