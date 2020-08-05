package org.middlepath.leveldbmcpe.source;

import org.middlepath.leveldbmcpe.chunk.AbstractChunk;
import org.middlepath.leveldbmcpe.generic.ByteRetriever;
import org.middlepath.leveldbmcpe.generic.Locatable;

public interface ChunkSource<T extends AbstractChunk<? extends Locatable>> extends LocatableSource<T> {

	public ByteRetriever getByteRetriever();
	
}