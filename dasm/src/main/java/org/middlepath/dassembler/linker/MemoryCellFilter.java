package org.middlepath.dassembler.linker;

import org.middlepath.mcapi.block.BlockType;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.nbt.StringNBTTag;
import org.middlepath.mcapi.visitor.FilterAction;

public class MemoryCellFilter implements FilterAction<SubChunkBlock> {

	private BlockGrouping<SubChunkBlock> boundingBox = null;
	
	public MemoryCellFilter(BlockGrouping<SubChunkBlock> boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	@Override
	/**
	 * Returns true to filter out any torch that is not a memory cel.  A memory cell for this computer is defined
	 * as a torch facing east, adjacent to a torch facing west.
	 */
	public boolean filter(SubChunkBlock t) {
		if (!isTorch(t)) {
			return true;
		}
		
		//Get the neighbor coord so we can check its state
		try {
			Coordinate neighborCoord = (Coordinate)t.getCoordinate().clone();
			neighborCoord.setGlobalX(neighborCoord.getGlobalX() + 1);
			SubChunkBlock neighbor = boundingBox.getLocatable(neighborCoord);
			
			if (!isTorch(neighbor))
				return true;
			
			//Now make sure they are facing each other
			String tState = getTorchState(t);
			String nState = getTorchState(neighbor);
			
			System.out.println("Maybe found a memory cell at " + t.getCoordinate());
			if ("west".equals(tState) && "east".equals(nState)) {
				System.out.println("Found a memory cell at " + t.getCoordinate());
				return false;
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean isTorch(SubChunkBlock t) {
		return t != null && (BlockType.REDSTONE_TORCH.equals(t.getBlockType()) ||
				BlockType.UNLIT_REDSTONE_TORCH.equals(t.getBlockType()));
	}
	
	private String getTorchState(SubChunkBlock t) {
		if (t == null || !isTorch(t))
			return null;
		
		//States should be a StringNBTTag for a torch direction
		StringNBTTag torchDirectionState = 
				t.getPaletteItem().getStateByName(StringNBTTag.class, "torch_facing_direction");
		return torchDirectionState.getValue();
	}

}
