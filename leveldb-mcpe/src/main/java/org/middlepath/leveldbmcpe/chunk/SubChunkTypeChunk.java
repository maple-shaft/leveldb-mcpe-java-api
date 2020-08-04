package org.middlepath.leveldbmcpe.chunk;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.LocatableIterator;
import org.middlepath.leveldbmcpe.generic.BedrockSerializable;
import org.middlepath.leveldbmcpe.source.ChunkSource;
import org.middlepath.leveldbmcpe.source.LocatableSource;
import org.middlepath.leveldbmcpe.group.Grouping;
import org.middlepath.leveldbmcpe.chunk.factory.BlockStorageRecordFactory;
import org.middlepath.leveldbmcpe.block.SubChunkBlock;
import org.middlepath.leveldbmcpe.block.BlockStorageRecord;
import org.middlepath.leveldbmcpe.block.BlockStateCoordinateIterator;
import org.middlepath.leveldbmcpe.block.BlockState;

public class SubChunkTypeChunk extends AbstractChunk<SubChunkBlock>
		implements LocatableSource<SubChunkBlock>, Iterable<SubChunkBlock>, BedrockSerializable {

	static BlockStorageRecordFactory factory = BlockStorageRecordFactory.getInstance();
	
	private List<BlockStorageRecord> blockStorageRecords = new ArrayList<BlockStorageRecord>();
	
	private HashMap<Coordinate, SubChunkBlock> cache = new HashMap<Coordinate, SubChunkBlock>();
	
	private byte version;
	private Byte storageCount;
	
	public SubChunkTypeChunk(Coordinate c, byte[] recordRawValue, ChunkSource<SubChunkTypeChunk> source) throws Exception {
		super(c, recordRawValue, source);
		this.version = recordRawValue[0];
		if (this.version != 1) {
			this.storageCount = recordRawValue[1];
		} else {
			this.storageCount = 1;
		}
		
		// Should create one, then send the index based on the length.
		for (int i = 0; i < getStorageCount(); i++) {
			getBlockStorageRecords().add(factory.createBlockStorageRecordFromBytes(recordRawValue, this));
		}
	}
	
	public byte getVersion() {
		return this.version;
	}
	
	public Byte getStorageCount() {
		return this.storageCount;
	}
	
	@Override
	public byte keyType() {
		return (byte)0x2F;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public List<BlockStorageRecord> getBlockStorageRecords() {
		return this.blockStorageRecords;
	}
	
	public void setBlockStorageRecords(List<BlockStorageRecord> blockStorageRecords) {
		this.blockStorageRecords = blockStorageRecords;
	}
	
	/**
	 * In almost all records there is only one storage record.
	 *
	 * @return
	 */
	public BlockStorageRecord getDefaultRecord() {
		return this.blockStorageRecords.get(0);
	}
	
	public SubChunkBlock getLocatable(Coordinate c) {
		Coordinate key = new Coordinate(c.getGlobalX(), c.getGlobalZ(), c.getGlobalY());
		key.setChunkX(0);
		key.setChunkZ(0);
		key.setYFactor(0);
		if (!cache.containsKey(key)) {
			BlockState state = getDefaultRecord().getBlockStates().getBlockState(
					c.getInnerX(), c.getInnerZ(), c.getInnerY());
			SubChunkBlock subChunkBlock = new SubChunkBlock(
					this, getDefaultRecord().getPalette(), state);
			cache.put(c, subChunkBlock);
		}
		return cache.get(c);
	}
	
	@Override
	public Iterator<SubChunkBlock> iterator() {
		Iterator<BlockState> bsiterator = getDefaultRecord().iterator();
		return new LocatableIterator<SubChunkBlock>(
				new BlockStateCoordinateIterator(xDiv, zDiv, yDiv, bsiterator),
				this);
	}
	
	@Override
	public Coordinate getCoordinate() {
		return new Coordinate(this.xDiv, this.zDiv, this.yDiv, 0,0,0);
	}
	
	@Override
	public void setCoordinate(Coordinate coordinate) {
		this.xDiv = coordinate.getChunkX();
		this.zDiv = coordinate.getChunkZ();
		this.yDiv = coordinate.getYFactor() * 16;
	}
	
	public Coordinate getCoordinateByBlockState(BlockState blockState) {
		return new Coordinate(this.xDiv, this.zDiv, this.yDiv, blockState.x, blockState.z, blockState.y);
	}
	
	@Override
	public Grouping<SubChunkBlock> createChildGroup(String name, Coordinate c1, Coordinate c2) {
		return null; //TODO: Will implement later
	}
	
	@Override
	public byte[] write() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(new byte[] { this.version, this.storageCount });
			for (BlockStorageRecord rec : this.blockStorageRecords) {
				bos.write(rec.write());
			}
			bos.flush();
			return bos.toByteArray();
		} finally {
			bos.close();
		}
	}
}