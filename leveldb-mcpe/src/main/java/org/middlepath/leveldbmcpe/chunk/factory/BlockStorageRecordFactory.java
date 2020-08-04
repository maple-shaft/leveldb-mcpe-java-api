package org.middlepath.leveldbmcpe.chunk.factory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.middlepath.leveldbmcpe.block.BlockStates;
import org.middlepath.leveldbmcpe.block.BlockStorageRecord;
import org.middlepath.leveldbmcpe.chunk.SubChunkTypeChunk;
import org.middlepath.leveldbmcpe.nbt.NBTParser;
import org.middlepath.leveldbmcpe.palette.BlockStoragePalette;
import org.middlepath.leveldbmcpe.palette.PaletteItemFactory;
import org.middlepath.leveldbmcpe.utils.BinaryUtils;

public class BlockStorageRecordFactory {

	static PrintStream o = System.out;
	private PaletteItemFactory paletteFactory = PaletteItemFactory.getInstance(
			PaletteItemFactory.LATEST_FORMAT_CREATOR);
			
	private static BlockStorageRecordFactory _instance;
	
	private BlockStorageRecordFactory() {
	
	}
	
	public static BlockStorageRecordFactory getInstance() {
		if (_instance == null) {
			_instance = new BlockStorageRecordFactory();
		}
		return _instance;
	}
	
	public BlockStorageRecord createBlockStorageRecordFromBytes(byte[] rawRecordValue, SubChunkTypeChunk parent) throws Exception {
		int startIndex = (parent.getVersion() == 1) ? 1 : 2;
		
		Integer storageVersion = (int)rawRecordValue[startIndex++];
		Integer bitsPerBlock = (storageVersion >> 1);
		Integer blocksPerWord = (int)Math.floor(32 / bitsPerBlock);
		Integer blockStateIndexSize = (int)Math.ceil(4096.0 / ((double)blocksPerWord));
		
		List<Long> blockStateWords = new ArrayList<Long>();
		for (int i = 0; i < blockStateIndexSize; i++) {
			blockStateWords.add(BinaryUtils.bytesToBigEndian(
					rawRecordValue[startIndex++],
					rawRecordValue[startIndex++],
					rawRecordValue[startIndex++],
					rawRecordValue[startIndex++]
			));
		}
		
		BlockStates storage = new BlockStates(blockStateWords, blocksPerWord, bitsPerBlock);
		
		int paletteSize = BinaryUtils.bytesToShortBigEndian(
				rawRecordValue[startIndex++],
				rawRecordValue[startIndex++]
		);
		
		//create the palette items
		BlockStoragePalette blockStoragePalette = new BlockStoragePalette(paletteSize);
		
		// skip the empty bytes until we get to the first NBT tag
		while (rawRecordValue[startIndex] == 0)
			startIndex++;
		
		NBTParser parser = new NBTParser(rawRecordValue, startIndex);
		while (parser.hasNext()) {
			blockStoragePalette.add(
					paletteFactory.createPaletteItemFromTag(parser.next()));
		}
		
		return new BlockStorageRecord(parent,
				storage,
				blockStoragePalette,
				storageVersion,
				bitsPerBlock,
				blocksPerWord,
				blockStateIndexSize);
	}
}