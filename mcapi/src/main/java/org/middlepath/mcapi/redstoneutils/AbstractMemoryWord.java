package org.middlepath.mcapi.redstoneutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 */
public abstract class AbstractMemoryWord<T extends Locatable, S extends Number, K extends Comparable<K>>
		implements MemoryWord<T, S, K> {
	
	protected final Collection<MemoryCell<T>> cells;
	
	public AbstractMemoryWord(Collection<? extends MemoryCell<T>> cells) {
		this.cells = Collections.unmodifiableCollection(cells);
	}
	
	public int getWordSize() {
		return cells.size();
	}
	
	public Collection<MemoryCell<T>> getMemoryCells() {
		List<MemoryCell<T>> temp = new ArrayList<>(this.cells);
		Collections.sort(temp, this);
		return Collections.unmodifiableCollection(temp);
	}
	
	public Collection<T> getContextObjects() {
		return getMemoryCells().stream().map(t -> t.getContextObject()).collect(Collectors.toList());
	}
	
}
