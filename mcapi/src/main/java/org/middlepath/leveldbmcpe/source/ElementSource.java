package org.middlepath.leveldbmcpe.source;

import org.middlepath.leveldbmcpe.block.Element;
import org.middlepath.leveldbmcpe.chunk.CompleteChunk;
import org.middlepath.leveldbmcpe.generic.Coordinate;

/**
 * <p>A LocatableSource that has a reference to a source of CompleteChunks.  This is useful for 
 * any iterable of Elements with a coordinate space that spans across chunks.  This source will get 
 * the parent chunk of that element and use that as a source of the Element.</p>
 *
 * @author DAB
 */
public class ElementSource implements LocatableSource<Element> {

	private LocatableSource<CompleteChunk> parentSource;
	
	public ElementSource(LocatableSource<CompleteChunk> parentSource) {
		this.parentSource = parentSource;
	}
	
	public LocatableSource<CompleteChunk> getParentSource() {
		return this.parentSource;
	}
	
	@Override
	public Element getLocatable(Coordinate c) {
		CompleteChunk parentChunk = parentSource.getLocatable(c);
		return parentChunk.getElementGlobalSource().getLocatable(c);
	}
	
	public <R extends Element> R getLocatableByType(Class<R> thiz, Coordinate c) {
		CompleteChunk parentChunk = parentSource.getLocatable(c);
		return parentChunk.getElementGlobalSource().getLocatableByType(thiz, c);
	}
	
}