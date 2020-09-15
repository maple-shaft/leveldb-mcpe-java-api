package org.middlepath.mcapi.block;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.palette.BlockStoragePalette;
import org.middlepath.mcapi.utils.BinaryUtils;

public class BlockStorageRecord implements Iterable<BlockState>, BedrockSerializable {

	static PrintStream o = System.out;
	
	private BlockStates storage;
	//protected Integer storageVersion;
	//protected Integer bitsPerBlock;
	//protected Integer blocksPerWord;
	//protected Integer blockStateIndexSize;
	//protected int paletteSize;
	protected BlockStoragePalette blockStoragePalette;
	
	public BlockStorageRecord(SubChunkTypeChunk parent,
			BlockStates storage,
			BlockStoragePalette blockStoragePalette,
			Integer storageVersion,
			Integer bitsPerBlock,
			Integer blocksPerWord, Integer blockStateIndexSize) {
		this.storage = storage;
		this.blockStoragePalette = blockStoragePalette;
		//this.storageVersion = storageVersion;
		//this.bitsPerBlock = bitsPerBlock;
		//this.blocksPerWord = blocksPerWord;
		//this.blockStateIndexSize = blockStateIndexSize;
	}
	
	public int getPaletteSize() {
		if (blockStoragePalette == null) {
			return 0;
		}
		return blockStoragePalette.size();
	}
	
	public Integer getStorageVersion() {
		return getBitsPerBlock() << 1;
	}
	
	public int getBitsPerBlock() {
		return BinaryUtils.getBitSize(getPaletteSize());
	}
	
	public int getBlocksPerWord() {
		return (int)Math.floor(32 / getBitsPerBlock());
	}
	
	public int getBlockStateIndexSize() {
		return (int)Math.ceil(4096.0 / ((double)getBlocksPerWord()));
	}
	
	public BlockStoragePalette getPalette() {
		return this.blockStoragePalette;
	}
	
	public BlockStates getBlockStates() {
		return this.storage;
	}
	
	@Override
	public Iterator<BlockState> iterator() {
		return new Iterator<BlockState>() {
			private final Iterator<BlockState> blockStateIterator = getBlockStates().iterator();
			
			@Override
			public boolean hasNext() {
				return blockStateIterator.hasNext();
			}
			
			@Override
			public BlockState next() {
				return blockStateIterator.next();
			}
		};
	}
	
	@Override
	public byte[] write() throws Exception {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(new byte[] { getStorageVersion().byteValue() });
			//BinaryUtils.concat(bos, storage.iterator());
			bos.write(storage.write(getBitsPerBlock(), getBlocksPerWord()));
			bos.write(new byte[] { (byte)0 });
			BinaryUtils.concat(bos, getPalette().iterator());
			bos.flush();
			return bos.toByteArray();
		} finally {
			bos.close();
		}
	}
}