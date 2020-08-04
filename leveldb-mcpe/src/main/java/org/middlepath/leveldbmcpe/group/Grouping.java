package org.middlepath.leveldbmcpe.group;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.Locatable;
import org.middlepath.leveldbmcpe.source.LocatableSource;

/**
 * <p>A grouping of T that is iterable and can contain an arbitrary number of child groups within it.</p>
 *
 * @author DAB
 *
 * @param <T> This class represents a Grouping of T
 */
public abstract class Grouping<T extends Locatable> implements LocatableSource<T>, Iterable<T> {

	protected HashSet<Coordinate> allCoords = new HashSet<Coordinate>();
	protected HashMap<String, Grouping<T>> childGroups = new HashMap<String, Grouping<T>>();
	protected Grouping<T> parent;
	protected Coordinate pointOne;
	protected Coordinate pointTwo;
	protected int maxX;
	protected int minX;
	protected int maxZ;
	protected int maxZ;
	protected int minZ;
	protected String name;
	
	public Grouping(Coordinate pointOne, Coordinate pointTwo, String name) {
		this(null, pointOne, pointTwo, name);
	}
	
	public Grouping(
			Grouping<T> parent,
			Coordinate pointOne, Coordinate pointTwo,
			String name) {
		this.parent = parent;
		this.pointOne = pointOne;
		this.pointTwo = pointTwo;
		defineBounds();
		this.setName(name);
		initializeCoordinates();
	}
	
	protected void defineBounds() {
		this.maxX = Math.max(pointOne.getGlobalX(), pointTwo.getGlobalX());
		this.minX = Math.min(pointOne.getGlobalX(), pointTwo.getGlobalX());
		this.maxZ = Math.max(pointOne.getGlobalZ(), pointTwo.getGlobalZ());
		this.minZ = Math.min(pointOne.getGlobalZ(), pointTwo.getGlobalZ());
	}
	
	/**
	 * Will return true if the box represented by the arguments is fully contained within this box.
	 *
	 * @param p1
	 * @param p2
	 * @return 
	 */
	public boolean isBoundingBox(Coordinate c1, Coordinate c2) {
		return !(c1 == null ||
				c1.getGlobalX() < minX || c1.getGlobalX() > maxX ||
				c1.getGlobalZ() < minZ || c1.getGlobalZ() > maxZ ||
				c2 == null ||
				c2.getGlobalX() < minZ || c2.getGlobalX() > maxX ||
				c2.getGlobalZ() < minZ || c2.getGlobalZ() > maxZ);
	}
	
	public Iterator<Coordinate> createCoordinateIterator() {
		return this.allCoords.iterator();
	}
	
	protected abstract void initializeCoordinates();
	
	public abstract Grouping<T> createChildGroup(String name, Coordinate c1, Coordinate c2);
	
	public Grouping<T> getChildGroupByName(String name) {
		return childGroups.get(name);
	}
	
	public Coordinate getPointOne() {
		return this.pointOne;
	}
	
	public void setPointOne(Coordinate pointOne) {
		this.pointOne = pointOne;
		defineBounds();
	}
	
	public Coordinate getPointTwo() {
		return pointTwo;
	}
	
	public void setPointTwo(Coordinate pointTwo) {
		this.pointTwo = pointTwo;
		defineBounds();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Grouping<T> getParent() {
		return this.parent;
	}
	
	public LocatableSource<T> getLocatableSource() {
		return this;
	}
	
}