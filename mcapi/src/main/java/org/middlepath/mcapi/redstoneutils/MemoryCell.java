package org.middlepath.mcapi.redstoneutils;

import org.middlepath.mcapi.generic.Locatable;

/**
 * Represents a generic object that signifies a single bit of data in a redstone memory module.
 * 
 * @author DAB
 *
 */
public interface MemoryCell<T extends Locatable> {
	
	/**
	 * Returns the underlying Locatable object that represents this instance of a MemoryCell.
	 * 
	 * @return A Locatable
	 */
	T getContextObject();
	
	/**
	 * Returns the value of the memory cell as either a true or false.
	 * 
	 * @return A boolean representing the data persisted by the memory cell.
	 */
	boolean getValue();
}
