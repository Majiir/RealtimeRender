package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

public final class HeightMapChunk implements HeightMap {

	private final int[][] heights = new int[16][16];
	private final Coordinate chunk;
	
	public HeightMapChunk(Coordinate location, HeightMap source) {
		this.chunk = location;
		Coordinate chunkOrigin = chunk.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK);
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				this.heights[x][y] = source.getHeight(chunkOrigin.plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
			}
		}
	}
	
	public HeightMapChunk(Coordinate location) {
		this.chunk = location;
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				this.heights[x][y] = HeightMap.NO_HEIGHT_INFORMATION;
			}
		}
	}

	@Override
	public int getHeight(Coordinate point) {
		point = getPixelCoordinate(point);
		return heights[point.getX()][point.getY()];
	}
	
	@Override
	public void setHeight(Coordinate point, int height) {
		point = getPixelCoordinate(point);
		heights[point.getX()][point.getY()] = height;
	}
	
	private Coordinate getPixelCoordinate(Coordinate point) {
		if (!point.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK).equals(chunk)) {
			throw new IllegalArgumentException("Point must be within the chunk.");
		}
		return point.subCoordinate(Coordinate.OFFSET_BLOCK_CHUNK);
	}
	
}
