package org.middlepath.mcapi.chunk.factory;

import java.util.HashMap;

import org.middlepath.mcapi.chunk.CompleteChunk;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.source.LocatableSource;

public class CompleteChunkFactory implements LocatableSource<CompleteChunk> {

	private ByteRetriever byteRetriever;
	private HashMap<Coordinate, CompleteChunk> cache = new HashMap<Coordinate, CompleteChunk>();
	
	public CompleteChunkFactory(ByteRetriever byteRetriever) {
		this.setByteRetriever(byteRetriever);
	}
	
	@Override
	public CompleteChunk getLocatable(Coordinate c) {
		if (c == null)
			return null;
			
		Coordinate key = c.cloneWithOffset(0,0,0);
		if (!cache.containsKey(key)) {
			// bytes may be empty meaning that no record exists for this chunk.  Thats ok.
			try {
				this.cache.put(key, new CompleteChunk(this, c, 0, getByteRetriever()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.cache.get(key);
	}
	
	public ByteRetriever getByteRetriever() {
		return byteRetriever;
	}
	
	public void setByteRetriever(ByteRetriever byteRetriever) {
		this.byteRetriever = byteRetriever;
	}
	
}