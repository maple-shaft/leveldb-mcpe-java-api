package org.middlepath.mcapi.block;

public enum BlockType {

	AIR("minecraft:air"),
	BEDROCK("minecraft:bedrock"),
	DIRT("minecraft:dirt"),
	GRASS("minecraft:grass"),
	CONCRETE("minecraft:concrete"),
	REDSTONE_WIRE("minecraft:redstone_wire"),
	UNPOWERED_REPEATER("minecraft:unpowered_repeater"),
	POWERED_REPEATER("minecraft:powered_repeater"),
	REDSTONE_TORCH("minecraft:redstone_torch"),
	UNLIT_REDSTONE_TORCH("minecraft:unlit_redstone_torch"),
	LEVER("minecraft:lever");
	
	BlockType(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public static BlockType findBlockType(String name) {
		BlockType[] blockTypes = BlockType.values();
		for (int i = 0; i < blockTypes.length; i++) {
			if (blockTypes[i].getName().equals(name))
				return blockTypes[i];
		}
		return null;
	}
}