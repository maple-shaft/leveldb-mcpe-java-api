package org.middlepath.leveldbmcpe.world;

import java.util.Iterator;

import org.middlepath.leveldbmcpe.chunk.CompleteChunk;
import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.LocatableIterator;
import org.middlepath.leveldbmcpe.group.Grouping;
import org.middlepath.leveldbmcpe.source.LocatableSource;

public class WorldSection extends Grouping<CompleteChunk> {

	public WorldSection(Grouping<CompleteChunk> parent,
			LocatableSource<CompleteChunk> sourcing, Coordinate pointOne, Coordinate pointTwo) {
		this(parent, "", pointOne, pointTwo);
	}
	
	public WorldSection(Grouping<CompleteChunk> parent,
			String name, Coordinate pointOne, Coordinate pointTwo) {
		super(parent, pointOne, pointTwo, name);
	}
	
	@Override
	public Grouping<CompleteChunk> createChildGroup(String name, Coordinate c1, Coordinate c2) {
		if (!isBoundingBox(c1,c2))
			return null;
		WorldSection section = new WorldSection(
				(Grouping<CompleteChunk>) this.parent, name, c1, c2);
		this.childGroups.put(name, section);
		return section;
	}
	
	protected void initializeCoordinates() {
		// create all basic 0 offset coordinates as keys for the section chunks
		for (int x = minX; x <= maxX; x += 16) {
			for (int z = minZ; z <= maxZ; z += 16) {
				this.allCoords.add(new Coordinate(x, z, 0, 0, 0, 0));
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	//Just return the locatable of the parent, which is a LocatableSource.
	public CompleteChunk getLocatable(Coordinate c) {
		return parent.getLocatable(c);
	}
	
	@Override
	public Iterator<CompleteChunk> iterator() {
		return new LocatableIterator<CompleteChunk>(
				this.createCoordinateIterator(),
				this);
	}
	
}
	
	