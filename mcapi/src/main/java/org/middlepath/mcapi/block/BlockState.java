package org.middlepath.mcapi.block;

import org.middlepath.mcapi.generic.Coordinate;

public class BlockState implements Comparable<BlockState> {
	
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