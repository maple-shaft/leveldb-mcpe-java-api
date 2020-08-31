package org.middlepath.dassembler.linker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.redstoneutils.Endian;
import org.middlepath.mcapi.redstoneutils.MemoryCell;
import org.middlepath.mcapi.redstoneutils.AbstractMemoryWord;

public class ExampleMemoryWord extends AbstractMemoryWord<SubChunkBlock, Byte, Integer> {
	
	public ExampleMemoryWord(Collection<MemoryCell<SubChunkBlock>> cells) {
		super(cells);
	}
	
	public ExampleMemoryWord(List<ExampleMemoryCell> cells) {
		super(cells);
	}
	
	public ExampleMemoryCell getAnyCell() {
		return ExampleMemoryCell.class.cast(cells.stream().findAny().get());
	}
	
	/**
	 * All cells in the word will have the same x row coordinate
	 * 
	 * @return
	 */
	public int getZRow() {
		return getAnyCell().getRowIndex();
	}
	
	public boolean isInstructionWord() {
		return getAnyCell().isInstruction();
	}

	@Override
	public int compare(MemoryCell<SubChunkBlock> o1, MemoryCell<SubChunkBlock> o2) {
		SubChunkBlock s1 = o1.getContextObject();
		SubChunkBlock s2 = o2.getContextObject();
		return s2.getCoordinate().getGlobalX() - s1.getCoordinate().getGlobalX();
	}

	@Override
	public Byte getValue() {
		Iterator<MemoryCell<SubChunkBlock>> iterator = this.getMemoryCells().iterator();
		int retInt = 0;
		for (int i = 0; i < 8; i++) {
			int bit = (iterator.next().getValue()) ? 1 : 0;
			retInt = (retInt << 1) | bit;
		}
		return (byte)retInt;
	}

	@Override
	public Endian getEndianness() {
		//Pretty sure that the bit order is always Big Endian
		return Endian.BIG_ENDIAN;
		
		//ExampleMemoryCell anyCell = getAnyCell();
		//return (anyCell.isInstruction()) ? Endian.LITTLE_ENDIAN : Endian.BIG_ENDIAN;
	}

	@Override
	public Integer getAddressableKey() {
		return this.getZRow();
	}

}
