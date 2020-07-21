package org.middlepath.leveldbmcpe.nbt;

public enum NBTTagType {

	TAG_END(0x00),
	TAG_BYTE(0x01),
	TAG_SHORT(0x02),
	TAG_INT(0x03),
	TAG_LONG(0x04),
	TAG_FLOAT(0x05),
	TAG_DOUBLE(0x06),
	TAG_BYTE_ARRAY(0x07),
	TAG_STRING(0x08),
	TAG_LIST(0x09),
	TAG_COMPOUND(0x0A),
	TAG_INT_ARRAY(0x0B),
	TAG_LONG_ARRAY(0x0C);
	
	private int key;
	
	NBTTagType(int key) {
		this.key = key;
	}
	
	public int getTagTypeKey() {
		return this.key;
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