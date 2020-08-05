package org.middlepath.mcapi.block;

import java.io.ByteArrayOutputStream;

import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.palette.BlockStoragePalette;
import org.middlepath.mcapi.palette.PaletteItem;

public class SubChunkBlock extends Element implements BedrockSerializable {

	private BlockStoragePalette blockStoragePalette;
	private BlockState blockState;
	
	public SubChunkBlock(SubChunkTypeChunk parent, BlockStoragePalette palette, BlockState blockState) {
		super(parent, parent, parent.getCoordinateByOffset(blockState.x, blockState.z, blockState.y));
		this.blockStoragePalette = palette;
		this.blockState = blockState;
	}
	
	public void setX(int x) {
		this.blockState.x = x;
	}
	
	public void setZ(int z) {
		this.blockState.z = z;
	}
	
	public void setY(int y) {
		this.blockState.y = y;
	}
	
	public int getX() {
		return this.blockState.x;
	}
	
	public int getZ() {
		return this.blockState.z;
	}
	
	public int getY() {
		return this.blockState.y;
	}
	
	public PaletteItem getPaletteItem() {
		return this.blockStoragePalette.get(this.blockState.paletteIndex);
	}
	
	public void setPaletteItem(PaletteItem paletteItem) {
		//TODO will implement;
	}
	
	public BlockType getBlockType() {
		return this.getPaletteItem().getBlockType();
	}
	
	@Override
	public String toString() {
		return "Block: (" + this.getCoordinate().toString() + ", " + getPaletteItem().toString() + ")";
	}
	
	@Override
	public byte[] write() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			return null; //TODO will implement;
		} finally {
			bos.close();
		}
	}
}