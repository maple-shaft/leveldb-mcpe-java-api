package org.middlepath.mcapi.redstoneutils;

import java.util.Collection;
import java.util.Comparator;

import org.middlepath.mcapi.generic.Locatable;

/**
 * Defines a Word in a redstone memory module.
 * 
 * @author DAB
 *
 * @param <T> Some Locatable element (Eg. a SubChunkBlock)
 * @param <S> A Java type that represents the value of this Word instance.
 * @param <K> A Comparable type that can uniquely identify the address or key to this Word instance.
 */
public interface MemoryWord<T extends Locatable, S extends Number, K extends Comparable<K>> extends Comparator<MemoryCell<T>> {

	/**
	 * Memory cells that represent this Word sorted in endian order.
	 * 
	 * @return A Collection of MemoryCells
	 */
	Collection<MemoryCell<T>> getMemoryCells();
	
	/**
	 * The Word size of the memory cell in bits.
	 */
	int getWordSize();
	
	/**
	 * Return the underlying Locatable objects that form this Word.  The values will be sorted by the Endianess of the Word.
	 * 
	 * @return A Collection of Locatables
	 */
	Collection<T> getContextObjects();
	
	/**
	 * The Endianness of the Word.
	 * 
	 * @return An Endian value
	 */
	Endian getEndianness();
	
	
	/**
	 * The underlying Java type that represents the value of this Word in Java form.
	 * 
	 * @return
	 */
	S getValue();
	
	/**
	 * Sets the value of this Word to the value of S.
	 * 
	 * @param word
	 */
	void setValue(S word);
	
	/**
	 * Some value that uniquely identifies this word, basically an address.
	 * 
	 * @return
	 */
	K getAddressableKey();
}
