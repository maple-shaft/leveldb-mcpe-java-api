package org.middlepath.dassembler.linker;

import org.middlepath.mcapi.block.BlockType;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.group.BlockGrouping;
import org.middlepath.mcapi.nbt.StringNBTTag;
import org.middlepath.mcapi.redstoneutils.MemoryCell;

/**
 * An example implementation of a memory cell for a specific redstone computer.
 * 
 * @author DAB
 *
 */
public class ExampleMemoryCell implements MemoryCell<SubChunkBlock> {

	private final SubChunkBlock contextObject;
	private final boolean instructionFlag;
	
	public static ExampleMemoryCell createMemoryCell(SubChunkBlock contextObject, BlockGrouping<SubChunkBlock> bg) {
		ExampleMemoryCell ret = null;
		try {
			String tState = getTorchState(contextObject);
			
			if (tState == null)
				return null;
			
			Coordinate lNeighborCoord = (Coordinate)contextObject.getCoordinate().clone();
			lNeighborCoord.setGlobalX(lNeighborCoord.getGlobalX() + 1);
			SubChunkBlock lNeighbor = bg.getLocatable(lNeighborCoord);
			String neighborState = getTorchState(lNeighbor);
			
			if (neighborState == null) {
				Coordinate rNeighborCoord = (Coordinate)contextObject.getCoordinate().clone();
				rNeighborCoord.setGlobalX(rNeighborCoord.getGlobalX() -1);
				SubChunkBlock rNeighbor = bg.getLocatable(rNeighborCoord);
				neighborState = getTorchState(rNeighbor);
			}
			
			if (neighborState == null)
				return null;
			
			if (tState.equals("west") && neighborState.equals("east")) {
				// If the block above and to the right is a concrete then it is an address torch
				Coordinate c = (Coordinate)contextObject.getCoordinate().clone();
				c.setGlobalY(c.getGlobalY() + 1);
				c.setGlobalX(c.getGlobalX() - 1);
				SubChunkBlock s = bg.getLocatable(c);
				if (BlockType.CONCRETE == s.getBlockType()) {
					ret = new ExampleMemoryCell(contextObject, true);
				}
			}
			if (tState.equals("east") && neighborState.equals("west")) {
				// If the block above and to the left is a concrete then it is a data torch
				Coordinate c = (Coordinate)contextObject.getCoordinate().clone();
				c.setGlobalY(c.getGlobalY() + 1);
				c.setGlobalX(c.getGlobalX() + 1);
				SubChunkBlock s = bg.getLocatable(c);
				if (BlockType.CONCRETE == s.getBlockType()) {
					ret = new ExampleMemoryCell(contextObject, false);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private ExampleMemoryCell(SubChunkBlock contextObject, boolean instructionFlag) {
		this.contextObject = contextObject;
		this.instructionFlag = instructionFlag;
	}
	
	public boolean isInstruction() {
		return this.instructionFlag;
	}
	
	@Override
	public SubChunkBlock getContextObject() {
		return this.contextObject;
	}
	
	/**
	 * All cells can be grouped into a Word by being on the same row
	 * 
	 * @return
	 */
	public int getRowIndex() {
		return getContextObject().getCoordinate().getGlobalZ();
	}

	@Override
	public boolean getValue() {
		return BlockType.REDSTONE_TORCH.equals(this.contextObject.getBlockType());
	}
	
	public static boolean isTorch(SubChunkBlock t) {
		return t != null && (BlockType.REDSTONE_TORCH.equals(t.getBlockType()) ||
				BlockType.UNLIT_REDSTONE_TORCH.equals(t.getBlockType()));
	}
	
	public static String getTorchState(SubChunkBlock t) {
		if (t == null || !isTorch(t))
			return null;
		
		//States should be a StringNBTTag for a torch direction
		StringNBTTag torchDirectionState = 
				t.getPaletteItem().getStateByName(StringNBTTag.class, "torch_facing_direction");
		return torchDirectionState.getValue();
	}
	
	public boolean isValid() {
		return !(getTorchState(this.contextObject) == null);
	}

	@Override
	public void setValue(boolean bit) {
		this.contextObject.getPaletteItem().blockType = (bit) ? BlockType.REDSTONE_TORCH : BlockType.UNLIT_REDSTONE_TORCH;
	}
}
