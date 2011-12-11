package net.nabaal.majiir.realtimerender.rendering;

import java.io.Serializable;

import net.nabaal.majiir.realtimerender.Coordinate;

public final class HeightMapTile extends HeightMap implements Serializable {

	private static final long serialVersionUID = 3L;
	
	private final byte[] heights;
	private final Coordinate location;
	
	public HeightMapTile(Coordinate location, HeightMap source) {
		this.location = location;
		int size = getSize();
		this.heights = new byte[1 << (size * 2)];
		for (int x = 0; x < (1 << size); x++) {
			for (int y = 0; y < (1 << size); y++) {
				this.heights[getArrayCoordinate(x, y)] = source.getHeight(location.zoomIn(size).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
			}
		}
	}
	
	public HeightMapTile(Coordinate location) {
		this.location = location;
		int size = getSize();
		this.heights = new byte[1 << (size * 2)];
		for (int x = 0; x < (1 << size); x++) {
			for (int y = 0; y < (1 << size); y++) {
				this.heights[getArrayCoordinate(x, y)] = HeightMap.NO_HEIGHT_INFORMATION;
			}
		}
	}
	
	public HeightMapTile(HeightMapTile other) {
		this.location = other.getLocation();
		int size = getSize();
		this.heights = new byte[1 << (size * 2)];
		for (int x = 0; x < (1 << size); x++) {
			for (int y = 0; y < (1 << size); y++) {
				this.heights[getArrayCoordinate(x, y)] = other.getHeight(location.zoomIn(size).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
			}
		}
	}
	
	public Coordinate getLocation() {
		return this.location;
	}
	
	public int getSize() {
		return location.getLevel() - Coordinate.LEVEL_BLOCK;
	}

	@Override
	public byte getHeight(Coordinate point) {
		return heights[getArrayCoordinate(point)];
	}
	
	@Override
	public void setHeight(Coordinate point, byte height) {
		heights[getArrayCoordinate(point)] = height;
	}
	
	private Coordinate getPixelCoordinate(Coordinate point) {
		if (!point.zoomOut(getSize()).equals(location)) {
			throw new IllegalArgumentException("Point must be within the chunk.");
		}
		return point.subCoordinate(getSize());
	}
	
	private int getArrayCoordinate(Coordinate point) {
		point = getPixelCoordinate(point);
		return getArrayCoordinate(point.getX(), point.getY());
	}
	
	private int getArrayCoordinate(int x, int y) {
		return (x << getSize()) + y;
	}
	
}
