package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class SelectiveAverageNormalMap implements NormalMap {

	private final NormalMap normalMap;
	private final double radius;
	private final double threshold;
	
	public SelectiveAverageNormalMap(NormalMap normalMap, double radius, double threshold) {
		this.normalMap = normalMap;
		this.radius = radius;
		this.threshold = threshold;
	}
	
	@Override
	public Float64Vector getNormal(Coordinate point) {
		Float64Vector v = Float64Vector.valueOf(0, 0, 0);
		int n = 0;
		int r = (int) Math.ceil(radius);
		int r_sq = (int) Math.pow(radius, 2);
		boolean bail = false;
		for (int x = -r; (x < r + 1) && !bail; x++) {
			for (int y = -r; (y < r + 1) && !bail; y++) {
				int d_sq = x*x + y*y;
				if (d_sq <= r_sq) {
					Float64Vector normal = normalMap.getNormal(point.plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
					if (normal != null) {
						Float64Vector up = Float64Vector.valueOf(0, 0, 1);
						double slope = Math.tan(Math.acos(normal.times(up).divide(normal.norm().times(up.norm())).doubleValue()));
						if (slope < threshold) {
							v = v.plus(normal);
							n++;
						} else {
							bail = true;
						}
					}
				}
			}
		}
		if (bail) {
			return normalMap.getNormal(point);
		} else {
			if (n < 1) {
				return null;
			}
			return v.times(1.0 / n);
		}
	}

}
