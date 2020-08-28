package org.middlepath.dassembler.linker;

import java.util.Collection;
import java.util.Iterator;

import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.redstoneutils.AbstractMemoryWordPair;

public class ExampleMemoryWordTuple extends AbstractMemoryWordPair<SubChunkBlock, Byte, ExampleMemoryWord> {
	
	public ExampleMemoryWordTuple() {
		this(null, null);
	}
	
	public ExampleMemoryWordTuple(ExampleMemoryWord instructionWord, ExampleMemoryWord dataWord) {
		super(instructionWord, dataWord);
	}
	
	public ExampleMemoryWordTuple(Collection<ExampleMemoryWord> wordArgs) {
		super();
		Iterator<ExampleMemoryWord> it = wordArgs.iterator();
		while (it.hasNext()) {
			ExampleMemoryWord w = it.next();
			if (w.isInstructionWord()) {
				this.instructionWord = w;
			} else {
				this.dataWord = w;
			}
		}
	}
	
	/**
	 * The Instruction and Data Words for this build happen to be on the same Z coordinate row.  That is how we 
	 * can correlate them.  We can also use this as ordering to determine address location in the module.
	 * 
	 * @return
	 */
	public Integer getZRow() {
		if (getInstructionWord() == null && getDataWord() == null)
			return null;
		
		ExampleMemoryWord someWord = (getInstructionWord() == null) ? getDataWord() : getInstructionWord();
		return someWord.getZRow();
	}

}
