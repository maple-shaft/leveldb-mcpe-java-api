package org.middlepath.mcapi.nbt;

import org.middlepath.mcapi.generic.BedrockSerializable;

public enum NBTTagType implements BedrockSerializable {

	TAG_END(0x00, null),
	TAG_BYTE(0x01, Byte.class),
	TAG_SHORT(0x02, Short.class),
	TAG_INT(0x03, Integer.class),
	TAG_LONG(0x04, Long.class),
	TAG_FLOAT(0x05, Float.class),
	TAG_DOUBLE(0x06, Double.class),
	TAG_BYTE_ARRAY(0x07, byte[].class),
	TAG_STRING(0x08, String.class),
	TAG_LIST(0x09, java.util.List.class),
	TAG_COMPOUND(0x0A, null),
	TAG_INT_ARRAY(0x0B, int[].class),
	TAG_LONG_ARRAY(0x0C, long[].class);
	
	private int key;
	private Class<?> type;
	
	NBTTagType(int key, Class<?> type) {
		this.key = key;
		this.type = type;
	}
	
	public int getTagTypeKey() {
		return this.key;
	}
	
	public Class<?> getType() {
		return this.type;
	}
	
	@Override
	public byte[] write() throws Exception {
		return new byte[] { (byte)this.key };
	}
	
	public static NBTTagType getTagTypeFromByte(final byte b) {
		switch (b) {
			case 0:
				return TAG_END;
			case 1:
				return TAG_BYTE;
			case 2:
				return TAG_SHORT;
			case 3:
				return TAG_INT;
			case 4:
				return TAG_LONG;
			case 5:
				return TAG_FLOAT;
			case 6:
				return TAG_DOUBLE;
			case 7:
				return TAG_BYTE_ARRAY;
			case 8:
				return TAG_STRING;
			case 9:
				return TAG_LIST;
			case 10:
				return TAG_COMPOUND;
			case 11:
				return TAG_INT_ARRAY;
			case 12:
				return TAG_LONG_ARRAY;
			default:
				return null;
		}
	}
}