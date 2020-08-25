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
	private final boolean addressFlag;
	
	public static ExampleMemoryCell createMemoryCell(SubChunkBlock contextObject, BlockGrouping<SubChunkBlock> bg) {
		ExampleMemoryCell ret = null;
		try {
			String tState = getTorchState(contextObject);
			
			if (tState == null)
				return null;
			
			Coordinate lNeighborCoord = (Coordinate)contextObject.getCoordinate().clone();
			lNeighborCoord.setGlobalX(lNeighborCoord.getGlobalX() + 1);
			SubChunkBlock lNeighbor = bg.getLocatable(lNeighborCoord);
			
			Coordinate rNeighborCoord = (Coordinate)contextObject.getCoordinate().clone();
			rNeighborCoord.setGlobalX(rNeighborCoord.getGlobalX() -1);
			SubChunkBlock rNeighbor = bg.getLocatable(rNeighborCoord);
			
			String lState = getTorchState(lNeighbor);
			String rState = getTorchState(rNeighbor);
			
			if (rState == null && lState != null && tState.equals("west") && lState.equals("east")) {
				ret = new ExampleMemoryCell(contextObject, true);
			} else if (lState == null && rState != null && tState.equals("east") && rState.equals("west")) {
				ret = new ExampleMemoryCell(contextObject, false);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private ExampleMemoryCell(SubChunkBlock contextObject, boolean addressFlag) {
		this.contextObject = contextObject;
		this.addressFlag = addressFlag;		
	}
	
	public boolean isAddress() {
		return this.addressFlag;
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
		return getContextObject().getX();
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
}
