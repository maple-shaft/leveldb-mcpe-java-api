package org.middlepath.leveldbmcpe.nbt;

public class ByteNBTTag extends NBTTag<Byte> {
	
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
	public byte[] getNBTBytes() {
		return null; //TODO: Will implement later, maybe this should implement serializable?
	}
}