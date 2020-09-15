package org.middlepath.mcapi.block;

import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.utils.BinaryUtils;

public class BlockState implements Comparable<BlockState> {

	static final int SIZE_MASK_ONE = 0b1;
	static final int SIZE_MASK_TWO = 0b11;
	static final int SIZE_MASK_THREE = 0b111;
	static final int SIZE_MASK_FOUR = 0b1111;
	static final int SIZE_MASK_FIVE = 0b11111;
	static final int SIZE_MASK_SIX = 0b111111;
	static final int SIZE_MASK_EIGHT = 0b11111111;
	static final int SIZE_MASK_SIXTEEN = 0b1111111111111111;
	
	//final byte bitSize;
	
	public int paletteIndex;
	public int x;
	public int z;
	public int y;
	
	public BlockState(int x, int z, int y, int paletteIndex) {
		this.x = x;
		this.z = z;
		this.y = y;
		this.paletteIndex = paletteIndex;
	}
	
	public BlockState(byte bitSize, int wordIndex, long word) {
		//this.bitSize = bitSize;
		this.paletteIndex = (int)((word >> (wordIndex * bitSize)) & BinaryUtils.getBitMask(bitSize));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BlockState))
			return false;
			
		BlockState a = (BlockState)obj;
		return a.x == this.x && a.y == this.y && a.z == this.z && a.paletteIndex == this.paletteIndex;
	}
	
	@Override
	public int hashCode() {
		return this.x ^ this.y ^ this.z ^ this.paletteIndex;
	}
	
	@Override
	public String toString() {
		return "Block State: Coords(x,z,y) = (" + x + "," + z + "," + y + ") Palette index = " + paletteIndex;
	}
	
	@Override
	public int compareTo(BlockState bs) {
		Coordinate c = new Coordinate(0,0,0,x,z,y);
		Coordinate otherC = new Coordinate(0,0,0,x,z,y);
		//TODO: finish implementing this
		return c.compareTo(otherC);
	}
	
}