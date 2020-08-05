package org.middlepath.leveldbmcpe.nbt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompoundNBTTag extends NBTTag<List<NBTTag<?>>> {
	
	public CompoundNBTTag(String name, List<NBTTag<?>> value, NBTTagType type) {
		super(name, value, type);
	}
	
	public CompoundNBTTag(byte[] bytes, int startIndex) {
		super(bytes, startIndex, NBTTagType.TAG_COMPOUND);
	}
	
	@Override
	/**
	 * Probably need to look at bytes because this assumes the index always starts at 0
	 */
	protected List<NBTTag<?>> parseNBTTagValue(byte[] bytes, int startIndex) {
		List<NBTTag<?>> retVal = new ArrayList<NBTTag<?>>();
		int index = 1 + startIndex + this.getNameLength();
		
		NBTParser parser = new NBTParser(bytes, index);
		while (parser.hasNext()) {
			retVal.add(parser.next());
		}
		return retVal;
	}
	
	@Override
	public byte[] getValueBytes() {
		byte[] ret = new byte[getValueLength()];
		int tempIndex = 0;
		for (NBTTag<?> innerValue : getValue()) {
			try {
				System.arraycopy(innerValue.write(), 0, ret, tempIndex, innerValue.getByteLength());
				tempIndex += innerValue.getByteLength();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@Override
	public byte[] getValueBytesLength() {
		return new byte[] {};
	}
	
	@Override
	/**
	 * The value length is the culmination of the compount tag types...
	 */
	public int getValueLength() {
		return this.value.stream().mapToInt(e -> e.getByteLength()).sum() + 1; // plus one for end tag
	}
	
	@Override
	public String toString() {
		return "{ Tag - Tag Type: " + this.type +
				" Tag Name: " + this.name +
				" Tag Value: " + this.value.stream().map(e -> e.toString()).collect(Collectors.joining(",")) +
				" }";
	}
}