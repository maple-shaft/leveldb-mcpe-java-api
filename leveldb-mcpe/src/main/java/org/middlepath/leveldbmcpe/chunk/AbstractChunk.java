package org.middlepath.leveldbmcpe.chunk;

public abstract class AbstractChunk {

	//X and Z coordinates are factors of 16;
	public int xDiv;
	public int zDiv;
	public int yDiv;
	public Dimension dimension;
	protected byte[] recordRawValue;
	
	public AbstractChunk(int xDiv, int zDiv, int yDiv, byte[] recordRawValue) {
		this.xDiv = xDiv;
		this.zDiv = zDiv;
		this.yDiv = yDiv;
		this.dimension = dimension;
		this.recordRawValue = recordRawValue;
	}
	
	public abstract byte keyType();
	
	/**
	 * Will give the Coordinate object for a specific block in this chunk.
	 */
	public Coordinate getCoordinate(int xOffset, int zOffset, int yOffset) {
		return new Coordinate(this.xDiv, this.zDiv, this.yDiv, xOffset, zOffset, yOffset);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AbstractChunk))
			return false;
		
		AbstractChunk arg = (AbstractChunk)obj;
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