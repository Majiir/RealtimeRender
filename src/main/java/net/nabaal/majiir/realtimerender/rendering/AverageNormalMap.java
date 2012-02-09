package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class AverageNormalMap implements NormalMap {

	private final NormalMap a;
	private final NormalMap b;
	
	public AverageNormalMap(NormalMap a, NormalMap b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Float64Vector getNormal(Coordinate point) {
		Float64Vector n_a = a.getNormal(point);
		Float64Vector n_b = b.getNormal(point);
		if ((n_a == null) || (n_b == null)) {
			return null;
		}
		n_a = n_a.times(1.0 / n_a.normValue());
		n_b = n_b.times(1.0 / n_b.normValue());
		return n_a.plus(n_b);
	}

}
