package org.middlepath.leveldbmcpe.generic;

import org.middlepath.leveldbmcpe.source.LocatableSource;

/**
 * <p>Locatable objects will have a defined world scoped Coordinate that can uniquely identify
 * its position in the world.  They derive from a source that provides traceability.</p>
 *
 * @author DAB
 *
 */
public interface Locatable extends Comparable<Locatable> {

	public Coordinate getCoordinate();
	public void setCoordinate(Coordinate coordinate);
	
	/**
	 * <p>This function returns the origination source that this particular object came from.</p>
	 *
	 * @return The source where this particular object came from.
	 */
	public LocatableSource<? extends Locatable> getSource();
	
}