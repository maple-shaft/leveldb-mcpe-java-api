package org.middlepath.mcapi.nbt;

import java.util.Iterator;

public class NBTParser implements Iterator<NBTTag<?>> {

	public byte[] bytes;
	public int index = 0;
	
	public NBTParser(byte[] bytes, int index) {
		this.bytes = bytes;
		this.index = index;
	}
	
	@Override
	/**
	 * Checks to see if an end tag is encountered, signifying the end of a List or Compound tag.
	 *
	 * @return 
	 */
	public boolean hasNext() {
		return ((bytes.length > index) && (bytes[index] != 0));
	}
	
	@Override
	/**
	 * This is probably a bad way to parse and create objects using an Iterator, I am open to better suggestions.
	 */
	public NBTTag<?> next() {
		if (!hasNext())
			return null;
		
		final NBTTagType tagType = NBTTagType.getTagTypeFromByte(bytes[index]);
		NBTTag<?> retVal = null;
		switch (tagType) {
			case TAG_STRING:
				retVal = new StringNBTTag(bytes, index);
				break;
			case TAG_COMPOUND:
				retVal = new CompoundNBTTag(bytes, index);
				break;
			case TAG_BYTE:
				retVal = new ByteNBTTag(bytes, index);
				break;
			case TAG_INT:
				retVal = new IntNBTTag(bytes, index);
			default:
				break;
		}
		
		//something is wrong if this is true
		if (retVal == null)
			return null;
			
		//now increment the index by the length
		index += retVal.getByteLength();
		return retVal;
	}
}