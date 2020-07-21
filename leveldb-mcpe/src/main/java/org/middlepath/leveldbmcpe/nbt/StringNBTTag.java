package org.middlepath.leveldbmcpe.nbt;

import org.middlepath.leveldbmcpe.utils.BinaryUtils;
import java.io.UnsupportedEncodingException;

public class StringNBTTag extends NBTTag<String> {

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
	public byte[] getNBTBytes() {
		return null; //TODO: Will implement later
	}
	
	@Override
	public int getValueLength() {
		return 2 + this.value.length();
	}

}
