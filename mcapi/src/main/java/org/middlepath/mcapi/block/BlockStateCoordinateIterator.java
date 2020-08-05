package org.middlepath.mcapi.block;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.middlepath.mcapi.generic.Coordinate;

public class BlockStateCoordinateIterator implements Iterator<Coordinate> {

	private final Iterator<BlockState> blockStateIterator;
	private final int chunkX;
	private final int chunkZ;
	private final int chunkY;
	
	public BlockStateCoordinateIterator(int chunkX, int chunkZ, int chunkY, Iterator<BlockState> blockStateIterator) {
		this.blockStateIterator = blockStateIterator;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.chunkY = chunkY;
	}
	
	@Override
	public boolean hasNext() {
		return blockStateIterator.hasNext();
	}
	
	@Override
	public Coordinate next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return getCoordinateByBlockState(blockStateIterator.next());
	}
	
	public Coordinate getCoordinateByBlockState(BlockState blockState) {
		return new Coordinate(this.chunkX, this.chunkZ, this.chunkY, blockState.x, blockState.z, blockState.y);
	}
}