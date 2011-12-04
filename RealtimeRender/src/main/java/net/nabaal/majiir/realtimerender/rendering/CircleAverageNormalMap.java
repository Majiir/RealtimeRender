package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class CircleAverageNormalMap implements NormalMap {

	private final NormalMap normalMap;
	private final double radius;
	
	public CircleAverageNormalMap(NormalMap normalMap, double radius) {
		this.normalMap = normalMap;
		this.radius = radius;
	}
	
	@Override
	public Float64Vector getNormal(Coordinate point) {
		Float64Vector v = Float64Vector.valueOf(0, 0, 0);
		int n = 0;
		int r = (int) Math.ceil(radius);
		int r_sq = (int) Math.pow(radius, 2);
		for (int x = -r; x < r + 1; x++) {
			for (int y = -r; y < r + 1; y++) {
				int d_sq = x*x + y*y;
				if (d_sq <= r_sq) {
					Float64Vector normal = normalMap.getNormal(point.plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK))); 
					if (normal != null) {
						v = v.plus(normal);
						n++;
					}
				}
			}
		}
		if (n < 1) {
			return null;
		}
		return v.times(1.0 / n);
	}

}
