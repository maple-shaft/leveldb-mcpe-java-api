package org.middlepath.mcapi.nbt;

import java.io.UnsupportedEncodingException;

import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.utils.BinaryUtils;

public abstract class NBTTag<T> implements BedrockSerializable {
	
	protected String name;
	protected T value;
	protected NBTTagType type;

	public NBTTag(String name, T value, NBTTagType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public NBTTag(byte[] bytes, int startIndex, NBTTagType type) {
		this.type = type;
		this.name = parseNBTTagName(bytes, startIndex);
		this.value = parseNBTTagValue(bytes, startIndex);
	}
	
	@Override
	public byte[] write() throws Exception {
		byte[] ret = new byte[this.getByteLength()];
		int tempIndex = 0;
		
		//first byte is the tag type
		ret[tempIndex++] = getType().write()[0];
		//write name length and then name
		byte[] nameBytes = getName().getBytes();
		byte[] nameByteLength = BinaryUtils.convertShortToBytesLittleEndian((short)nameBytes.length);
		
		System.arraycopy(nameByteLength, 0, ret, tempIndex, 2);
		tempIndex += 2;
		if (nameBytes.length > 0) {
			System.arraycopy(nameBytes, 0, ret, tempIndex, nameBytes.length);
			tempIndex += nameBytes.length;
		}
		
		//write value length and value
		byte[] valueLengthBytes = getValueBytesLength();
		byte[] valueBytes = getValueBytes();
		if (valueLengthBytes != null && valueLengthBytes.length > 0) {
			System.arraycopy(valueLengthBytes, 0, ret, tempIndex, 2);
			tempIndex += 2;
		}
		
		System.arraycopy(valueBytes, 0, ret, tempIndex, valueBytes.length);
		return ret;
	}
	
	public String getName() {
		return this.name;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * Will calculate the byte length of the value in the tag + 2 bytes for the length.
	 * 
	 * @return 
	 */
	public abstract int getValueLength();
	
	/**
	 * Get the underlying bytes of the value of this tag (for serialization into Binary NBT format)
	 * 
	 * @return
	 */
	public abstract byte[] getValueBytes();
	
	/**
	 * Will return the byte representation of the value length for that specific type of Tag.
	 * @return
	 */
	public abstract byte[] getValueBytesLength();
	
	public NBTTagType getType() {
		return this.type;
	}
	
	protected abstract T parseNBTTagValue(byte[] bytes, int startIndex);
	
	protected String parseNBTTagName(byte[] bytes, int startIndex) {
		int index = startIndex + 1;
		short nameLength = BinaryUtils.bytesToShortBigEndian(bytes[index++], bytes[index++]);
		
		//sometimes the name length is 0 so we skip...
		if (nameLength == 0)
			return "";
			
		byte[] nameValue = new byte[nameLength];
		System.arraycopy(bytes, index, nameValue, 0, nameLength);
		try {
			return new String(nameValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNameLength() {
		return 2 + this.name.length();
	}
	
	public int getByteLength() {
		return 1 + this.getNameLength() + this.getValueLength();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NBTTag))
			return false;
			
		@SuppressWarnings("unchecked")
		NBTTag<T> a = (NBTTag<T>)obj;
		return getType().equals(a.getType()) && getValue().equals(a.getValue());
	}
	
	@Override
	public int hashCode() {
		return getType().hashCode() ^ getValue().hashCode();
	}
	
	@Override
	public String toString() {
		return "Tag - Tag Type: " + this.type +
				" Tag Name: " + this.name +
				" Tag Value: " + this.value.toString();
	}
	
}