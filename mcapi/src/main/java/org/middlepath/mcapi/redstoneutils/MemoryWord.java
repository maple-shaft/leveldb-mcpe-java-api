package org.middlepath.mcapi.redstoneutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.middlepath.mcapi.generic.Locatable;

/**
 * Instances of MemoryWord represent a single contiguous Word in a redstone memory module.
 * 
 * @author DAB
 *
 * @param <T> A Locatable type that forms the underlying MCAPI type behind this Word.
 * @param <S> A Java Number type that accurately represents the underlying data of the Word in Java form.
 * @param <K> A Java Comparable type that represents the unique address of this word to be used as a classifier.
 */
public abstract class MemoryWord<T extends Locatable, S extends Number>
		implements Comparator<MemoryCell<T>> {
	
	protected final Collection<MemoryCell<T>> cells;
	
	public MemoryWord(Collection<? extends MemoryCell<T>> cells) {
		this.cells = Collections.unmodifiableCollection(cells);
	}
	
	/**
	 * The Word size of the memory cell in bits.
	 */
	public int getWordSize() {
		return cells.size();
	}
	
	/**
	 * Memory cells that represent this Word sorted in endian order.
	 * 
	 * @return A Collection of MemoryCells
	 */
	public Collection<MemoryCell<T>> getMemoryCells() {
		List<MemoryCell<T>> temp = new ArrayList<>(this.cells);
		Collections.sort(temp, this);
		return Collections.unmodifiableCollection(temp);
	}
	
	/**
	 * Return the underlying Locatable objects that form this Word.  The values will be sorted by the Endianess of the Word.
	 * 
	 * @return A Collection of Locatables
	 */
	public Collection<T> getContextObjects() {
		return getMemoryCells().stream().map(t -> t.getContextObject()).collect(Collectors.toList());
	}
	
	/**
	 * The Endianness of the Word.
	 * 
	 * @return An Endian value
	 */
	public abstract Endian getEndianness();
	
	
	/**
	 * The underlying Java type that represents the value of this Word in Java form.
	 * 
	 * @return
	 */
	public abstract S getValue();
	

	
}
