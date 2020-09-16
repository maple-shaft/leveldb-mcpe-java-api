package org.middlepath.mcapi.block;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.middlepath.mcapi.utils.BinaryUtils;

public class BlockStates implements Iterable<BlockState> {

	private Map<Integer, BlockState> blockStates = null;
	
	public BlockStates() {
		this.blockStates = new HashMap<Integer, BlockState>(4096);
	}
	
	public BlockStates(List<Long> words, int blocksPerWord, int bitSize) {
		this.blockStates = new HashMap<Integer, BlockState>(4096);
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 16; y++) {
					int wordIndex = getWordIndex(x,z,y,blocksPerWord);
					Long word = words.get(wordIndex);
					int specificIndex = getTempIndex(x, z, y);
					int indexInWord = getIndexInWord(x, z, y, blocksPerWord);
					
					int paletteIndex = (int)((word >> (indexInWord * bitSize)) & BinaryUtils.getBitMask(bitSize));
					
					BlockState blockState = new BlockState(x, z, y, paletteIndex);
					
					this.blockStates.put(specificIndex, blockState);
				}
			}
		}
	}
	
	public BlockState getBlockState(int x, int z, int y) {
		return this.blockStates.get(getTempIndex(x, z, y));
	}
	
	public void setBlockState(BlockState bs) {
		this.blockStates.put(getTempIndex(bs.x, bs.z, bs.y), bs);
	}
	
	/**
	 * The coords are relative to the subchunk.
	 */
	private int getTempIndex(int x, int z, int y) {
		int ret = x;
		ret <<= 4;
		ret |= z;
		ret <<= 4;
		ret |= y;
		return ret;
	}
	
	private int getIndexInWord(int x, int z, int y, int blocksPerWord) {
		return getTempIndex(x, z, y) % blocksPerWord;
	}
	
	private int getWordIndex(int x, int z, int y, int blocksPerWord) {
		return (int)Math.ceil(getTempIndex(x,z,y) / blocksPerWord);
	}
	
	@Override
	public Iterator<BlockState> iterator() {
		return this.blockStates.values().iterator();
	}
	
	public byte[] write(int bitSize, int blocksPerWord) throws Exception {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Iterator<BlockState> it = this.iterator();
			List<Long> words = new ArrayList<Long>();
			int indexInWord = 0;
			long tempWord = 0;
			while (it.hasNext()) {
				BlockState bs = it.next();
				tempWord |= bs.paletteIndex << (bitSize * indexInWord++);
				
				if (indexInWord == blocksPerWord) {
					indexInWord = 0;
					words.add(tempWord);
					tempWord = 0;
				}
			}
			words.add(tempWord);
			
			words.stream().map(BinaryUtils::convertIntToBytesLittleEndian)
					.forEach(b -> { try { bos.write(b); } catch (IOException e) {}});
			bos.flush();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}