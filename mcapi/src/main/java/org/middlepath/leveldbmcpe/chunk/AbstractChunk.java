package org.middlepath.leveldbmcpe.chunk;

import org.middlepath.leveldbmcpe.generic.Coordinate;
import org.middlepath.leveldbmcpe.generic.Locatable;
import org.middlepath.leveldbmcpe.group.Grouping;
import org.middlepath.leveldbmcpe.source.ChunkSource;

public abstract class AbstractChunk<T extends Locatable> extends Grouping<T> implements Locatable {

	private int minY;
	private int maxY;

	//X and Z coordinates are factors of 16;
	public int xDiv;
	public int zDiv;
	public int yDiv;
	public Dimension dimension;
	protected ChunkSource<? extends AbstractChunk<T>> chunkSource;
	
	public AbstractChunk(Coordinate c, byte[] recordRawValue, ChunkSource<? extends AbstractChunk<T>> chunkSource) {
		super(c, c.cloneWithOffset(16,16,16), "CHUNKGROUP: " + c.toString());
		this.xDiv = c.getChunkX();
		this.zDiv = c.getChunkZ();
		this.yDiv = c.getYFactor() * 16;
		this.dimension = Dimension.DEFAULT;
		this.chunkSource = chunkSource;
	}
	
	@Override
	public int compareTo(Locatable o) {
		return getCoordinate().compareTo(o.getCoordinate());
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
	public ChunkSource<? extends AbstractChunk<T>> getSource() {
		return this.chunkSource;
	}
	
	public abstract byte keyType();
	
	public Coordinate getCoordinateByOffset(int xOffset, int zOffset, int yOffset) {
		return new Coordinate(this.xDiv, this.zDiv, this.yDiv, xOffset, zOffset, yOffset);
	}
	
	public Coordinate getCoordinate() {
		return getCoordinateByOffset(0,0,0);
	}
	
	public void setCoordinate(Coordinate c) {
		this.xDiv = c.getChunkX();
		this.zDiv = c.getChunkZ();
		this.yDiv = c.getYFactor() * 16;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AbstractChunk))
			return false;
		
		AbstractChunk<T> arg = (AbstractChunk<T>)obj;
		return (xDiv == arg.xDiv) &&
				(zDiv == arg.zDiv) &&
				(yDiv == arg.yDiv) &&
				(dimension == arg.dimension) && (keyType() == arg.keyType());
	}
	
	@Override
	public int hashCode() {
		return xDiv ^ zDiv ^ keyType() ^ ((dimension == null || dimension.getValue() == null) ? 0 : dimension.getValue());
	}
}

enum Dimension {
	DEFAULT(null),
	NETHER(1),
	END(2);
	
	Integer value;
	Dimension(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() { return this.value; }
}