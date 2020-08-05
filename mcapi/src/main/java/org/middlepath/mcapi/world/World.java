package org.middlepath.mcapi.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.IteratorUtils;
import org.middlepath.mcapi.chunk.CompleteChunk;
import org.middlepath.mcapi.chunk.factory.CompleteChunkFactory;
import org.middlepath.mcapi.generic.Coordinate;
import org.middlepath.mcapi.generic.LocatableIterator;
import org.middlepath.mcapi.group.Grouping;

public class World extends Grouping<CompleteChunk> {

	private static final Coordinate MINC = new Coordinate(Integer.MIN_VALUE, Integer.MIN_VALUE,0,0,0,0);
	private static final Coordinate MAXC = new Coordinate(Integer.MAX_VALUE, Integer.MAX_VALUE,0,0,0,0);
	
	private CompleteChunkFactory factory;
	
	public World(CompleteChunkFactory factory) {
		super(MINC, MAXC, "root");
		this.factory = factory;
	}
	
	@Override
	public Grouping<CompleteChunk> getParent() {
		return null;
	}
	
	@Override
	public String getName() {
		return "root";
	}
	
	@Override
	public WorldSection createChildGroup(String name, Coordinate c1, Coordinate c2) {
		WorldSection section = new WorldSection(this, name, c1, c2);
		this.childGroups.put(name, section);
		return section;
	}
	
	@Override
	public void initializeCoordinates() {
		// Worlds are infinite so there is nothing to initialize
	}
	
	@Override
	/**
	 * Chain all the child coordinate iterators together giving us an iterator that will loop through all defined chunks in the world.
	 */
	public Iterator<Coordinate> createCoordinateIterator() {
		Collection<Iterator<? extends Coordinate>> iterators = new ArrayList<Iterator<? extends Coordinate>>();
		
		for (Grouping<CompleteChunk> group : this.childGroups.values()) {
			iterators.add(group.createCoordinateIterator());
		}
		
		return IteratorUtils.chainedIterator(iterators);
	}
	
	@Override
	public CompleteChunk getLocatable(Coordinate c) {
		return this.factory.getLocatable(c);
	}
	
	@Override
	public Iterator<CompleteChunk> iterator() {
		LocatableIterator<CompleteChunk> it = new LocatableIterator<CompleteChunk>(
				createCoordinateIterator(),
				factory);
		return it;
	}
	
}