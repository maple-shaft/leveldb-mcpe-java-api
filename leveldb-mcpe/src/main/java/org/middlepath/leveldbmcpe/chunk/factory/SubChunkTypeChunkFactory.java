package org.middlepath.leveldbmcpe.chunk.factory;

import org.middlepath.leveldbmcpe.chunk.SubChunkTypeChunk;
import org.middlepath.leveldbmcpe.generic.ByteRetriever;
import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.source.ChunkSource;

public class SubChunkTypeChunkFactory implements ChunkSource<SubChunkTypeChunk> {

	private ByteRetriever byteRetriever;
	
	public SubChunkTypeChunkFactory(ByteRetriever byteRetriever) {
		this.byteRetriever = byteRetriever;
	}
	
	@Override
	public SubChunkTypeChunk getLocatable(Coordinate c) {
		byte[] bytes = byteRetriever.get(c.getChunkX(), c.getChunkZ(), c.getYFactor(), 0);
		if (bytes == null) || bytes.length == 0)
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
	
}