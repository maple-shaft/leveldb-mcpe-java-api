package org.middlepath.mcapi.redstoneutils;

import org.middlepath.mcapi.generic.Locatable;

/**
 * In some redstone builds we may treat a single pair of Words by a single addressable key, one word representing an 
 * instruction, and the second word representing the operand or potentially data.
 * 
 * @author DAB
 *
 * @param <T> A Locatable element (Eg. a SubChunkBlock)
 * @param <S> A Java type that represents the value of the Words in this word pair.
 * @param <K> A Comparable type that uniquely identifies the address or key of this word pair.
 * @param <R> A MemoryWord type that signifies the type of MemoryWord of this pair.
 */
public interface MemoryWordPair<
		T extends Locatable,
		S extends Number,
		K extends Comparable<K>,
		R extends MemoryWord<T, S, K>> {
	
	public R getAnyWord();
	
	/**
	 * Get the Instruction Word of this pair.
	 * 
	 * @return
	 */
	public R getInstructionWord();
	
	/**
	 * Set the Instruction Word of this pair.
	 * 
	 * @param addressWord
	 */
	public void setInstructionWord(R addressWord);
	
	/**
	 * Get the Data/Operand Word of this pair.
	 * 
	 * @return
	 */
	public R getDataWord();
	
	/**
	 * Set the Data/Operand Word of this pair.
	 * 
	 * @param dataWord
	 */
	public void setDataWord(R dataWord);
}
