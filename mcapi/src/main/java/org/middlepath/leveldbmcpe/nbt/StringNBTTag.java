package org.middlepath.leveldbmcpe.nbt;

import org.middlepath.leveldbmcpe.utils.BinaryUtils;
import java.io.UnsupportedEncodingException;

public class StringNBTTag extends NBTTag<String> {

	public StringNBTTag(String name, String value, NBTTagType type) {
		super(name, value, type);
	}

	public StringNBTTag(byte[] bytes, int startIndex) {
		super(bytes, startIndex, NBTTagType.TAG_STRING);
	}
	
	@Override
	protected String parseNBTTagValue(byte[] bytes, int startIndex) {
		int index = 1 + startIndex + this.getNameLength();
		short valueLength = BinaryUtils.bytesToShortBigEndian(bytes[index++], bytes[index++]);
		byte[] value = new byte[valueLength];
		System.arraycopy(bytes, index, value, 0, valueLength);
		try {
			return new String(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getValueLength() {
		return 2 + this.value.length();
	}
	
	@Override
	public byte[] getValueBytes() {
		return getValue().getBytes();
	}
	
	@Override
	public byte[] getValueBytesLength() {
		return BinaryUtils.convertShortToBytesLittleEndian((short)getValueBytes().length);
	}
}
