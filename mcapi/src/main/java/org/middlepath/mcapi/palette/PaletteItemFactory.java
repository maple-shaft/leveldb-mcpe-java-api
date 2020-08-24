package org.middlepath.mcapi.palette;

import java.util.Optional;

import org.middlepath.mcapi.nbt.CompoundNBTTag;
import org.middlepath.mcapi.nbt.NBTTag;
import org.middlepath.mcapi.nbt.NBTTagType;

/**
 * <p>The way that NBT tags currently are stored for local palette items is a compound tag with zero
 * length name, then a string type with name as "name" and the block type.  Block status is stored in
 * a compound tag with name "state" and a variable type value.  It also has a 32 bit uint 
 * "version" tag.</p>
 *
 * <p>The can change in the future so a new factory format can be created here to handle any kind of
 * palette.  For now I am just gouing to handle what I see as the latest.</p>
 *
 * @author DAB
 *
 */
public class PaletteItemFactory {

	private static PaletteItemFactory instance;
	public static final String LATEST_FORMAT_CREATOR = "LATEST_LOCAL";
	
	private String formatCreator;
	
	private PaletteItemFactory(String formatCreator) {
		this.formatCreator = formatCreator;
	}
	
	public static PaletteItemFactory getInstance(String formatCreator) {
		if (instance == null)
			instance = new PaletteItemFactory(formatCreator);
		return instance;
	}
	
	public static PaletteItemFactory getInstance() {
		return getInstance(LATEST_FORMAT_CREATOR);
	}
	
	public PaletteItem createPaletteItemFromTag(NBTTag<?> tag) throws Exception {
		if (formatCreator == null)
			throw new Exception("No palette format was specified!");
		
		switch (formatCreator) {
			case LATEST_FORMAT_CREATOR:
				return createLatestLocal((CompoundNBTTag)tag);
			default:
				throw new Exception("Currently unsupported palette item format.");
		}
	}
	
	private PaletteItem createLatestLocal(CompoundNBTTag tag) throws Exception {
		if (tag == null)
			throw new Exception("Could not create palette item for LATEST_LOCAL.");
			
		Optional<NBTTag<?>> blockTypeTagOpt = tag.getValue().stream()
				.filter(t -> "name".equals(t.getName())).findFirst();
		Optional<NBTTag<?>> blockStatesTagOpt = tag.getValue().stream()
				.filter(t -> "states".equals(t.getName())).findFirst();
		Optional<NBTTag<?>> blockVersionTagOpt = tag.getValue().stream()
				.filter(t -> "version".equals(t.getName())).findFirst();
				
		if (blockTypeTagOpt.isPresent() &&
				blockStatesTagOpt.isPresent() &&
				blockVersionTagOpt.isPresent()) {
			String blockTypeName = (String)(blockTypeTagOpt.get().getValue());
			int version = (Integer)(blockVersionTagOpt.get().getValue());
			Object states = blockStatesTagOpt.get().getValue();
			NBTTagType statesType = blockStatesTagOpt.get().getType();
			return new PaletteItem(blockTypeName, statesType, states, version);
		} else {
			throw new Exception("Invalid format for LATEST_LOCAL");
		}
	}
	
}