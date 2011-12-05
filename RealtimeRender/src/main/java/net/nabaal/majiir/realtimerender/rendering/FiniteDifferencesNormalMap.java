package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class FiniteDifferencesNormalMap implements NormalMap {

	private final HeightMap heightMap;
	
	public FiniteDifferencesNormalMap(HeightMap heightMap) {
		this.heightMap = heightMap;
	}

	@Override
	public Float64Vector getNormal(Coordinate point) {		
		double dx = 0;
		double dz = 0;
		int dx_c = 0;
		int dz_c = 0;
		
		int y = heightMap.getHeight(point);
		//RealtimeRender.getLogger().info("RealtimeRender: height = '" + y + "' at " + point);
		if (y > 127) {
			return null;
		}
		
		int h;
		h = heightMap.getHeight(point.plus(new Coordinate(-1, 0, Coordinate.LEVEL_BLOCK)));
		if (h < HeightMap.NO_HEIGHT_INFORMATION) { // TODO: Replace with HeightMap.isValid() or something
			dx += (y - h);
			dx_c += 1;
		}
		h = heightMap.getHeight(point.plus(new Coordinate(1, 0, Coordinate.LEVEL_BLOCK)));
		if (h < HeightMap.NO_HEIGHT_INFORMATION) { // TODO: In rest of file
			dx += (h - y);
			dx_c += 1;
		}
		if (dx_c == 0) {
			return null;
		}
		dx /= dx_c;
		
		h = heightMap.getHeight(point.plus(new Coordinate(0, -1, Coordinate.LEVEL_BLOCK)));
		if (h < 128) {
			dz += (y - h);
			dz_c += 1;
		}
		h = heightMap.getHeight(point.plus(new Coordinate(0, 1, Coordinate.LEVEL_BLOCK)));
		if (h < 128) {
			dz += (h - y);
			dz_c += 1;
		}
		if (dz_c == 0) {
			return null;
		}
		dz /= dz_c;
		
		return Float64Vector.valueOf(-dx, -dz, 1);
	}

}
