package org.middlepath.mcapi.chunk;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.commons.collections4.iterators.IteratorChain;
import org.middlepath.mcapi.block.Element;
import org.middlepath.mcapi.block.SubChunkBlock;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.chunk.factory.SubChunkTypeChunkFactory;
import org.middlepath.mcapi.generic.BedrockSerializable;
import org.middlepath.mcapi.generic.ByteRetriever;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.generic.Locatable;
import org.middlepath.mcapi.generic.LocatableIterator;
import org.middlepath.mcapi.group.Grouping;
import org.middlepath.mcapi.source.CompositeLocatableSource;
import org.middlepath.mcapi.source.ElementGlobalSource;
import org.middlepath.mcapi.source.LocatableSource;

/**
 * Not a Chunk type, but has all the types of chunks.
 *
 * @author DAB
 *
 */
public class CompleteChunk implements Iterable<Element>, Locatable {

	private SubChunkTypeChunkFactory subChunkFactory;
	
	// All subchunks indexed on the y chunk index of the chunk.
	private HashMap<Integer, SubChunkTypeChunk> allSubChunks = new HashMap<Integer, SubChunkTypeChunk>();
	
	private Coordinate coordinate;
	private int dimension;
	private CompleteChunkFactory parent;
	
	public CompleteChunk(CompleteChunkFactory parent, Coordinate c, int dimension, ByteRetriever byteRetriever) throws Exception {
		this.coordinate = c;
		this.dimension = dimension;
		this.parent = parent;
		this.subChunkFactory = new SubChunkTypeChunkFactory(byteRetriever);
		
		// Get the first sub chunk in this chunk (eg. y = 0 -> 16)
		Coordinate subc = new Coordinate(c.getChunkX(), c.getChunkZ(), 0,0,0,0);
		SubChunkTypeChunk subChunk = this.subChunkFactory.getLocatable(subc);
		if (subChunk != null)
			allSubChunks.put(0, subChunk);
	}
	
	@Override
	public int compareTo(Locatable o) {
		return getCoordinate().compareTo(o.getCoordinate());
	}
	
	public boolean isEmpty() {
		return allSubChunks.size() == 0;
	}
	
	public SubChunkTypeChunk getSubChunk(int yDiv) throws Exception {
		if (!allSubChunks.containsKey(yDiv)) {
			Coordinate c = new Coordinate(coordinate.getChunkX(),
					coordinate.getChunkZ(), yDiv * 16, 0, 0, 0);
			SubChunkTypeChunk subChunk = this.subChunkFactory.getLocatable(c);
			if (subChunk != null)
				allSubChunks.put(yDiv, subChunk);
		}
		return allSubChunks.get(yDiv);
	}
	
	@Override
	public Iterator<Element> iterator() {
		// For now, lets just return the subchunk iterator
		Collection<Iterator<Coordinate>> ret = 
				allSubChunks.values().stream().map(c -> c.createCoordinateIterator())
				.collect(Collectors.toList());
		
		IteratorChain<Coordinate> chain = new IteratorChain<Coordinate>();
		ret.forEach(chain::addIterator);
		
		return new LocatableIterator<Element>(chain, getElementGlobalSource());
	}
	
	public ElementGlobalSource getElementGlobalSource() {
		CompositeLocatableSource<SubChunkBlock> cs = 
				new CompositeLocatableSource<SubChunkBlock>(SubChunkBlock.class, Collections.emptyList());
		allSubChunks.values().forEach(e -> cs.add(e));
		ElementGlobalSource r = new ElementGlobalSource();
		r.add(SubChunkBlock.class, cs);
		return r;
	}
	
	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	@Override
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	@Override
	public LocatableSource<? extends Locatable> getSource() {
		return this.parent;
	}
	
}