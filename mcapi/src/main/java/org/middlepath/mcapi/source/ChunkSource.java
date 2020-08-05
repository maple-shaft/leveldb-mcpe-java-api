package org.middlepath.mcapi.source;

import org.middlepath.mcapi.chunk.AbstractChunk;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Locatable;

public interface ChunkSource<T extends AbstractChunk<? extends Locatable>> extends LocatableSource<T> {

	public ByteRetriever getByteRetriever();
	
}