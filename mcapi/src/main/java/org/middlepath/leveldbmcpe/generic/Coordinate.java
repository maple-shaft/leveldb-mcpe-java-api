package org.middlepath.leveldbmcpe.generic;

public class Coordinate implements Comparable<Coordinate> {
	
	private int xFactor;
	private int zFactor;
	private int yFactor;
	
	private int innerX;
	private int innerZ;
	private int innerY;
	
	public Coordinate(int globalX, int globalZ, int globalY) {
		this.setGlobalX(globalX);
		this.setGlobalZ(globalZ);
		this.setGlobalY(globalY);
	}
	
	public Coordinate(int chunkX, int chunkZ, int chunkY, int innerX, int innerZ, int innerY) {
		this.xFactor = chunkX / 16;
		this.zFactor = chunkZ / 16;
		this.yFactor = chunkY / 16;
	}
	
	public Coordinate cloneWithOffset(int innerX, int innerZ, int innerY) {
		int globalX = getChunkX() + innerX;
		int globalZ = getChunkZ() + innerZ;
		int globalY = (this.yFactor * 16) + innerY;
		return new Coordinate(globalX, globalZ, globalY);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Coordinate))
			return false;
		Coordinate a = (Coordinate)obj;
		return this.getGlobalX() == a.getGlobalX() &&
				this.getGlobalZ() == a.getGlobalZ() &&
				this.getGlobalY() == a.getGlobalY();
	}
	
	@Override
	public int hashCode() {
		return this.getGlobalX() ^ this.getGlobalZ() ^ this.getGlobalY();
	}
	
	@Override
	public String toString() {
		return "(" + getGlobalX() + "," + getGlobalZ() + "," + getGlobalY() + ")";
	}
	
	@Override
	/**
	 * <p>Sort order will be the way in which blocks are stored:</p>
	 * <p>Check global chunk index first, then inner coordinates.  This makes sorting of blocks
	 * much easier and more efficient.</p>
	 */
	public int compareTo(Coordinate o) {
		int retVal = this.getXFactor() - o.getXFactor();
		if (retVal == 0) {
			if ((retVal = this.getZFactor() - o.getZFactor()) == 0) {
				if ((retVal = this.getYFactor() - o.getYFactor()) == 0) {
					// Now check inner coordinates because they are in the same sub chunk.
					if ((retVal = this.getInnerX() - o.getInnerX()) == 0) {
						if ((retVal = this.getInnerZ() - o.getInnerZ()) == 0) {
							retVal = this.getInnerY() - o.getInnerY();
						}
					}
				}
			}
		}
		return retVal;
	}
	
	public int getInnerX() {
		return this.innerX;
	}
	
	public int getGlobalX() {
		return getChunkX() + innerX;
	}
	
	public int getChunkX() {
		return this.xFactor * 16;
	}
	
	public void setInnerX(int innerX) {
		this.innerX = innerX;
	}
	
	public void setGlobalX(int globalX) {
		this.xFactor = globalX / 16;
		this.innerX = globalX % 16;
	}
	
	public void setChunkX(int chunkX) {
		this.xFactor = chunkX / 16;
	}
	
	public int getInnerZ() {
		return innerZ;
	}
	
	public int getGlobalZ() {
		return this.getChunkZ() + innerZ;
	}
	
	public int getChunkZ() {
		return this.zFactor * 16;
	}
	
	public void setInnerZ(int innerZ) {
		this.innerZ = innerZ;
	}
	
	public void setGlobalZ(int globalZ) {
		this.zFactor = globalZ / 16;
		this.innerZ = globalZ % 16;
	}
	
	public void setChunkZ(int chunkZ) {
		this.zFactor = chunkZ / 16;
	}
	
	public int getGlobalY() {
		return (this.yFactor * 16) + innerY;
	}
	
	public int getInnerY() {
		return innerY;
	}
	
	public void setInnerY(int innerY) {
		this.innerY = innerY;
	}
	
	public void setGlobalY(int globalY) {
		this.yFactor = globalY / 16;
		this.innerY = globalY % 16;
	}
	
	public int getXFactor() {
		return this.xFactor;
	}
	
	public int getYFactor() {
		return this.yFactor;
	}
	
	public int getZFactor() {
		return this.zFactor;
	}
	
	public void setYFactor(int yFactor) {
		this.yFactor = yFactor;
	}
}