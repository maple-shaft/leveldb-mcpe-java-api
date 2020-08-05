package org.middlepath.leveldbmcpe.block;

import org.middlepath.leveldbmcpe.chunk.AbstractChunk;
import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.Locatable;
import org.middlepath.leveldbmcpe.source.LocatableSource;

public abstract class Element implements Locatable {

	protected Coordinate coordinate;
	protected AbstractChunk<? extends Element> parent;
	protected LocatableSource<? extends Element> source;
	
	public Element(LocatableSource<? extends Element> source, AbstractChunk<? extends Element> parent, Coordinate coordinate) {
		this.parent = parent;
		this.coordinate = coordinate;
		this.source = source;
	}
	
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	@Override
	public LocatableSource<? extends Element> getSource() {
		return this.source;
	}
	
	public AbstractChunk<? extends Element> getParent() {
		return this.parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Element))
			return false;
			
		Element b = (Element)obj;
		return this.coordinate.equals(b.getCoordinate());
	}
	
	@Override
	public int hashCode() {
		return this.coordinate.hashCode();
	}
	
	@Override
	public int compareTo(Locatable e) {
		return getCoordinate().compareTo(e.getCoordinate());
	}
	
}