package org.middlepath.leveldbmcpe.nbt;

import org.middlepath.leveldbmcpe.utils.BinaryUtils;

public class IntNBTTag extends NBTTag<Integer> {

	public IntNBTTag(byte[] bytes, int startIndex) {
		super(bytes, startIndex, NBTTagType.TAG_INT);
	}
	
	@Override
	public int getValueLength() {
		return 4;
	}
	
	@Override
	protected Integer parseNBTTagValue(byte[] bytes, int startIndex) {
		int index = 1 + startIndex + this.getNameLength();
		return (int)BinaryUtils.bytesToIntBigEndian(
				bytes[index++],
				bytes[index++],
				bytes[index++],
				bytes[index++]);
	}
	
	@Override
	public byte[] getNBTBytes() {
		return null; //TODO: Will implement later
	}
}