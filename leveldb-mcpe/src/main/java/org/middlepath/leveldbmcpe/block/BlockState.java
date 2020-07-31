package org.middlepath.leveldbmcpe.block;

public class BlockState implements Comparable<BlockState>, BedrockSerializable {

	static final int SIZE_MASK_ONE = 0b1;
	static final int SIZE_MASK_TWO = 0b11;
	static final int SIZE_MASK_THREE = 0b111;
	static final int SIZE_MASK_FOUR = 0b1111;
	static final int SIZE_MASK_FIVE = 0b11111;
	static final int SIZE_MASK_SIX = 0b111111;
	static final int SIZE_MASK_EIGHT = 0b11111111;
	static final int SIZE_MASK_SIXTEEN = 0b1111111111111111;
	
	final byte bitSize;
	
	public int paletteIndex;
	public int paletteIndex;
	public int x;
	public int z;
	public int y;
	
	public BlockState(byte bitSize, int wordIndex, long word) {
		this.bitSize = bitSize;
		this.paletteIndex = (int)((word >> (wordIndex * bitSize)) & getBitMask());
	}
	
	@Override
	/**
	 * A block state by itself is just a palette index.  The location is an attribute of where the block state exists in the record 
	 * (4,096 block states per storage record)
	 *
	 * @return
	 * @throws Exception
	 */
	public byte[] write() throws Exception {
		return BinaryUtils.convertIntToBytesLittleEndian(this.paletteIndex);
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
	
	private int getBitMask() {
		switch (bitSize) {
			case 1:
				return SIZE_MASK_ONE;
			case 2:
				return SIZE_MASK_TWO;
			case 3:
				return SIZE_MASK_THREE;
			case 4:
				return SIZE_MASK_FOUR;
			case 5:
				return SIZE_MASK_FIVE;
			case 6:
				return SIZE_MASK_SIX;
			case 8:
				return SIZE_MASK_EIGHT;
			case 16:
				return SIZE_MASK_SIXTEEN;
		}
		return -1;
	}
	
}