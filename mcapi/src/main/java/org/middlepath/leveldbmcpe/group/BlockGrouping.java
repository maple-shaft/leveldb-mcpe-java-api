package org.middlepath.leveldbmcpe.group;

import java.util.Iterator;

import org.middlepath.leveldbmcpe.block.Element;
import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.LocatableIterator;
import org.middlepath.leveldbmcpe.source.ElementSource;

public class BlockGrouping<T extends Element> extends Grouping<T> {

	private int minY;
	private int maxY;
	private ElementSource blockSource;
	
	public BlockGrouping(ElementSource source,
			String name, Coordinate pointOne, Coordinate pointTwo) {
		this(source, null, name, pointOne, pointTwo);
	}
	
	public BlockGrouping(ElementSource source,
			BlockGrouping<T> parent, String name, Coordinate pointOne, Coordinate pointTwo) {
		super(parent, pointOne, pointTwo, name);
		this.blockSource = source;
	}
	
	@Override
	protected void defineBounds() {
		super.defineBounds();
		this.minY = Math.min(this.pointOne.getGlobalY(), this.pointTwo.getGlobalY());
		this.maxY = Math.max(this.pointOne.getGlobalY(), this.pointTwo.getGlobalY());
	}
	
	@Override
	public boolean isBoundingBox(Coordinate c1, Coordinate c2) {
		return super.isBoundingBox(c1, c2) &&
				c1 != null &&
				!(c1.getGlobalY() < minY || c1.getGlobalY() > maxY) &&
				c2 != null &&
				!(c2.getGlobalY() < minY || c2.getGlobalY() > maxY);
	}
	
	@Override
	public Iterator<T> iterator() {
		LocatableIterator<T> it = new LocatableIterator<T>(
				this.allCoords.iterator(),
				getLocatableSource());
		return it;
	}
	
	@Override
	protected void initializeCoordinates() {
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = minY; y <= maxY; y++) {
					this.allCoords.add(new Coordinate(x, z, y));
				}
			}
		}
	}
	
	@Override
	public Grouping<T> createChildGroup(String name, Coordinate c1, Coordinate c2) {
		if (!this.isBoundingBox(c1, c2))
			return null;
		
		BlockGrouping<T> ret = new BlockGrouping<T>(this.blockSource, this, name, c1, c2);
		this.childGroups.put(name, ret);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getLocatable(Coordinate c) {
		return (T) blockSource.getLocatable(c);
	}
	
	public <R extends T> R getLocatableByType(Class<R> clazz, Coordinate c) {
		return clazz.cast(blockSource.getLocatableByType(clazz, c));
	}
	
}