package org.middlepath.mcapi.palette;

import java.util.ArrayList;
import java.util.List;

import org.middlepath.mcapi.block.BlockType;
import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.nbt.ByteNBTTag;
import org.middlepath.mcapi.nbt.CompoundNBTTag;
import org.middlepath.mcapi.nbt.IntNBTTag;
import org.middlepath.mcapi.nbt.NBTTag;
import org.middlepath.mcapi.nbt.NBTTagType;
import org.middlepath.mcapi.nbt.StringNBTTag;

public class PaletteItem implements BedrockSerializable {

	public BlockType blockType;
	public Object states;
	public NBTTagType statesType;
	public int version;
	
	public PaletteItem(String name, NBTTagType statesType, Object states, int version) {
		this(BlockType.findBlockType(name), statesType, states, version);
	}
	
	public PaletteItem(BlockType type, NBTTagType statesType, Object states, int version) {
		this.blockType = type;
		this.statesType = statesType;
		this.states = states;
		this.version = version;
	}

	@SuppressWarnings("unchecked")
	public <R extends NBTTag<?>> R getStateByName(final Class<R> clazz, final String name) {
		if (!NBTTagType.TAG_COMPOUND.equals(statesType))
			return null;
		
		List<NBTTag<?>> statesList = (List<NBTTag<?>>) states;
		return statesList.stream()
				.filter(t -> t.getName().equals(name))
				.map(t -> clazz.cast(t))
				.findAny().get();
	}
	
	public BlockType getBlockType() {
		return this.blockType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PaletteItem))
			return false;
		PaletteItem a = (PaletteItem)obj;
		return this.blockType == a.blockType &&
				this.states.equals(a.states) &&
				this.statesType == a.statesType &&
				this.version == a.version;
	}
	
	@Override
	public int hashCode() {
		return this.blockType.hashCode() ^
				this.statesType.hashCode() ^
				this.states.hashCode() ^
				this.version;
	}
	
	@Override
	public String toString() {
		return "PALETTE ITEM: Type - " + this.blockType +
				", State Type - " + this.statesType + 
				", State Value - " + this.states.toString() + 
				", Version - " + this.version;
	}
	
	public PaletteItem clone() {
		PaletteItem ret = new PaletteItem(
				this.getBlockType(),
				this.statesType,
				this.states,
				this.version);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Will create a Compound tag with these properties and then return its write method.
	 *
	 */
	public byte[] write() throws Exception {
		StringNBTTag nameTag = new StringNBTTag("name", this.blockType.getName(), NBTTagType.TAG_STRING);
		IntNBTTag versionTag = new IntNBTTag("version", this.version, NBTTagType.TAG_INT);
		NBTTag<?> statesTag = null;
		switch (statesType) {
			case TAG_BYTE:
				statesTag = new ByteNBTTag("states", Byte.class.cast(states), statesType);
				break;
			case TAG_INT:
				statesTag = new IntNBTTag("states", Integer.class.cast(states), statesType);
				break;
			case TAG_STRING:
				statesTag = new StringNBTTag("states", String.class.cast(states), statesType);
				break;
			case TAG_COMPOUND:
				statesTag = new CompoundNBTTag("states", ((List<NBTTag<?>>)states), statesType);
				break;
			default:
				throw new Exception("Currently unsupported type");
		}
		
		List<NBTTag<?>> compound = new ArrayList<NBTTag<?>>();
		compound.add(nameTag);
		compound.add(statesTag);
		compound.add(versionTag);
		CompoundNBTTag parentTag = new CompoundNBTTag("", compound, NBTTagType.TAG_COMPOUND);
		return parentTag.write();
	}
	
}