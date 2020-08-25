package org.middlepath.dassembler.linker;

import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.visitor.FilterAction;

public class MemoryCellFilter implements FilterAction<SubChunkBlock> {

	private BlockGrouping<SubChunkBlock> boundingBox = null;
	
	public MemoryCellFilter(BlockGrouping<SubChunkBlock> boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	@Override
	/**
	 * Returns true to filter out any torch that is not a memory cell.  A memory cell for this computer is defined
	 * as a torch facing west, adjacent to a torch facing east when in an Address Word, or a torch facing east adjacent 
	 * to a torch facing west for a Data Word.
	 */
	public boolean filter(SubChunkBlock t) {		
		//Get the neighbor coord so we can check its state
		try {
			ExampleMemoryCell cell = ExampleMemoryCell.createMemoryCell(t, boundingBox);
			return cell == null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
