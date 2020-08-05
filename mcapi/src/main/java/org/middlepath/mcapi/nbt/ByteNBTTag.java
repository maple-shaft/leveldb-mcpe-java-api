package org.middlepath.mcapi.nbt;

public class ByteNBTTag extends NBTTag<Byte> {

	public ByteNBTTag(String name, Byte value, NBTTagType type) {
		super(name, value, type);
	}
	
	public ByteNBTTag(byte[] bytes, int startIndex) {
		super(bytes, startIndex, NBTTagType.TAG_BYTE);
	}
	
	@Override
	public int getValueLength() {
		return 1;
	}
	
	@Override
	protected Byte parseNBTTagValue(byte[] bytes, int startIndex) {
		int index = 1 + startIndex + this.getNameLength();
		return bytes[index];
	}
	
	@Override
	public byte[] getValueBytes() {
		return new byte[] { getValue().byteValue() };
	}
	
	@Override
	public byte[] getValueBytesLength() {
		return new byte[] {};
	}
}