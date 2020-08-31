package org.middlepath.mcapi.redstoneutils;

import java.util.Collection;
import java.util.HashMap;

import org.middlepath.mcapi.generic.Locatable;

public abstract class AbstractMemoryWordPairModule<
		T extends Locatable,
		S extends Number,
		K extends Comparable<K>,
		R extends AbstractMemoryWord<T,S,K>,
		Q extends AbstractMemoryWordPair<T,S,K,R>>
		implements MemoryModule<T,S,K,R> {

	protected HashMap<K, Q> words = new HashMap<>();
	
	public AbstractMemoryWordPairModule() {
		
	}
	
	public void put(Q word) {
		R anyWord = (word.getInstructionWord() == null) ? word.getDataWord() : word.getInstructionWord();
		
		if (anyWord == null) {
			return;
		}
		
		words.put(anyWord.getAddressableKey(), word);
	}
	
	public Q get(K key) {
		return words.get(key);
	}
	
	public void putAll(Collection<Q> wordPairs) {
		wordPairs.forEach(this::put);
	}
	
	@Override
	public int compare(R o1, R o2) {
		K firstK = (o1 == null) ? null : o1.getAddressableKey();
		K secondK = (o2 == null) ? null : o2.getAddressableKey();
		
		if (firstK == null && secondK == null)
			return 0;
		
		return (firstK == null) ? secondK.compareTo(firstK) : firstK.compareTo(secondK);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Memory Module: [\n\t");
		words.entrySet().forEach(w -> sb.append("[ Key: " + w.getKey() + ", Value: " + w.getValue() + "]\n"));
		sb.append("]");
		return sb.toString();
	}
}
