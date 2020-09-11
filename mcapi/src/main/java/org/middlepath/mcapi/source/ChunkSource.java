package org.middlepath.mcapi.source;

import org.middlepath.mcapi.chunk.AbstractChunk;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Locatable;

/**
 * A ChunkSource instance has a ByteRetriever and is a source of Chunks of type T.  It implements LocatableSource so it 
 * knows how to retrieve and save Chunks by their coordinate location.
 * 
 * @author DAB
 *
 * @param <T> A Chunk type.
 */
public interface ChunkSource<T extends AbstractChunk<? extends Locatable>> extends LocatableSource<T> {

	/**
	 * The backing instance of the ChunkSource that knows how to retrieve and persist bytes.
	 * 
	 * @return
	 */
	public ByteRetriever getByteRetriever();
	
	/**
	 * Saves a Chunk of type T to the persistence store of the sources ByteRetriever.
	 * 
	 * @param t An AbstractChunk type
	 */
	public int saveChunk(T t);
	
}