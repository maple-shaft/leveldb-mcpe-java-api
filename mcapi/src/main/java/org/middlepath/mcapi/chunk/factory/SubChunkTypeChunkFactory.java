package org.middlepath.mcapi.chunk.factory;

import org.middlepath.leveldbmcpejni.RecordType;
import org.middlepath.mcapi.chunk.SubChunkTypeChunk;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.source.ChunkSource;

public class SubChunkTypeChunkFactory implements ChunkSource<SubChunkTypeChunk> {

	private ByteRetriever byteRetriever;
	
	public SubChunkTypeChunkFactory(ByteRetriever byteRetriever) {
		this.byteRetriever = byteRetriever;
	}
	
	@Override
	public SubChunkTypeChunk getLocatable(Coordinate c) {
		byte[] bytes = byteRetriever.get(RecordType.SUBCHUNK,c.getChunkX(), c.getChunkZ(), c.getYFactor(), 0);
		if ((bytes == null) || bytes.length == 0)
			return null;
			
		SubChunkTypeChunk ret = null;
		try {
			ret = new SubChunkTypeChunk(c, bytes, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public ByteRetriever getByteRetriever() {
		return this.byteRetriever;
	}

	@Override
	public int saveChunk(SubChunkTypeChunk t) {
		if (t == null)
			return -1;
		
		Coordinate c = t.getCoordinate();
		byte[] data = null;
		int ret = -1;
		try {
			data = t.write();
			ret = getByteRetriever().save(data, RecordType.SUBCHUNK, c.getChunkX(), c.getChunkZ(), c.getYFactor(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}