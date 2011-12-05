package net.nabaal.majiir.realtimerender.rendering;

import org.jscience.mathematics.vector.Float64Vector;

import net.nabaal.majiir.realtimerender.Coordinate;

public final class NormalMapTile implements NormalMap {

	private final Float64Vector[][] normals;
	private final Coordinate location;
	
	public NormalMapTile(Coordinate location, NormalMap source) {
		this.location = location;
		this.normals = fromNormalMap(source);
	}
	
	private Float64Vector[][] fromNormalMap(NormalMap source) {
		Float64Vector[][] normals = new Float64Vector[16][16];
		Coordinate chunkOrigin = location.zoomIn(4);
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				normals[x][y] = source.getNormal(chunkOrigin.plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
			}
		}
		return normals;
	}

	@Override
	public Float64Vector getNormal(Coordinate point) {
		if (!point.zoomOut(4).equals(location)) {
			throw new IllegalArgumentException();
		}
		point = point.subCoordinate(Coordinate.OFFSET_BLOCK_CHUNK);
		return normals[point.getX()][point.getY()];
	}
	
}
