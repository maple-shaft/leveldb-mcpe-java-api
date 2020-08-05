package org.middlepath.mcapi.block;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.utils.BinaryUtils;

public class BlockStates implements Iterable<BlockState>, BedrockSerializable {

	private List<BlockState> blockStates = new ArrayList<BlockState>(4096);
	
	private List<Long> words;
	private int blocksPerWord;
	
	public BlockStates() {
		this.words = new ArrayList<Long>();
	}
	
	public BlockStates(List<Long> words, int blocksPerWord, int bitSize) {
		this.words = words;
		this.blocksPerWord = blocksPerWord;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 16; y++) {
					int wordIndex = getWordIndex(x,z,y);
					int indexInWord = getIndexInWord(x,z,y);
					int specificIndex = (wordIndex * blocksPerWord) + indexInWord;
					
					BlockState blockState = new BlockState(
							(byte)bitSize,
							indexInWord,
							this.words.get(wordIndex)
					);
					blockState.x = x;
					blockState.z = z;
					blockState.y = y;
					this.blockStates.add(specificIndex, blockState);
				}
			}
		}
	}
	
	public BlockState getBlockState(int x, int z, int y) {
		int wordIndex = getWordIndex(x,z,y);
		int indexInWord = getIndexInWord(x,z,y);
		int specificIndex = (wordIndex * blocksPerWord) + indexInWord;
		return this.blockStates.get(specificIndex);
	}
	
	public void setBlockState(BlockState bs) {
		int wordIndex = getWordIndex(bs.x,bs.z,bs.y);
		int indexInWord = getIndexInWord(bs.x,bs.z,bs.y);
		int specificIndex = (wordIndex * blocksPerWord) + indexInWord;
		this.blockStates.add(specificIndex, bs);
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
	
	private int getIndexInWord(int x, int z, int y) {
		return getTempIndex(x, z, y) % blocksPerWord;
	}
	
	private int getWordIndex(int x, int z, int y) {
		return getTempIndex(x,z,y) % blocksPerWord;
	}
	
	@Override
	public Iterator<BlockState> iterator() {
		return this.blockStates.iterator();
	}
	
	@Override
	public byte[] write() throws Exception {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Iterator<BlockState> it = this.iterator();
			List<Long> words = new ArrayList<Long>();
			int indexInWord = 0;
			long tempWord = 0;
			while (it.hasNext()) {
				BlockState bs = it.next();
				int bitSize = bs.bitSize;
				tempWord |= bs.paletteIndex << (bitSize * indexInWord++);
				
				if (indexInWord == this.blocksPerWord) {
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