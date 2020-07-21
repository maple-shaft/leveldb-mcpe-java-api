package org.middlepath.leveldbmcpe.nbt;

import java.io.UnsupportedEncodingException;

import org.middlepath.leveldbmcpe.utils.BinaryUtils;

public abstract class NBTTag<T> {
	
	protected String name;
	protected T value;
	protected NBTTagType type;
	protected final int startIndex;
	
	public NBTTag(byte[] bytes, int startIndex, NBTTagType type) {
		this.type = type;
		this.startIndex = startIndex;
		this.name = parseNBTTagName(bytes, startIndex);
		this.value = parseNBTTagName(bytes, startIndex);
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
	
	public abstract byte[] getNBTBytes();
	
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