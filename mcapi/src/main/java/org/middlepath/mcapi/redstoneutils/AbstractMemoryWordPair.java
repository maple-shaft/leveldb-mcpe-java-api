package org.middlepath.mcapi.redstoneutils;

import org.middlepath.mcapi.generic.Locatable;

public abstract class AbstractMemoryWordPair<
		T extends Locatable,
		S extends Number,
		K extends Comparable<K>,
		R extends AbstractMemoryWord<T, S, K>> implements MemoryWordPair<T,S,K,R> {

	protected R instructionWord = null;
	protected R dataWord = null;
	
	public AbstractMemoryWordPair() {
		this(null, null);
	}
	
	public AbstractMemoryWordPair(R addressWord, R dataWord) {
		this.instructionWord = addressWord;
		this.dataWord = dataWord;
	}
	
	public R getInstructionWord() {
		return this.instructionWord;
	}
	
	public void setInstructionWord(R addressWord) {
		this.instructionWord = addressWord;
	}
	
	public R getDataWord() {
		return this.dataWord;
	}
	
	public void setDataWord(R dataWord) {
		this.dataWord = dataWord;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Memory Tuple: [");
		sb.append(getInstructionWord());
		sb.append(", ");
		sb.append(getDataWord());
		sb.append("]");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AbstractMemoryWordPair))
			return false;
		AbstractMemoryWordPair<T, S, K, R> a = (AbstractMemoryWordPair<T, S, K, R>) obj;
		return ((getInstructionWord() == null) ? (a.getInstructionWord() == getInstructionWord()) : (getInstructionWord().equals(a.getInstructionWord())) &&
				(getDataWord() == null) ? (a.getDataWord() == getDataWord()) : (getDataWord().equals(a.getDataWord())));
	}
	
	@Override
	public int hashCode() {
		return ((getInstructionWord() == null) ? 0 : getInstructionWord().hashCode()) ^
				((getDataWord() == null) ? 0 : getDataWord().hashCode());
	}
}
