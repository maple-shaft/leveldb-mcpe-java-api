package org.middlepath.mcapi.nbt;

import org.middlepath.mcapi.utils.BinaryUtils;

public class IntNBTTag extends NBTTag<Integer> {

	public IntNBTTag(String name, Integer value, NBTTagType type) {
		super(name, value, type);
	}

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
	public byte[] getValueBytes() {
		return BinaryUtils.convertIntToBytesLittleEndian(getValue());
	}
	
	@Override
	public byte[] getValueBytesLength() {
		return new byte[] {};
	}
}