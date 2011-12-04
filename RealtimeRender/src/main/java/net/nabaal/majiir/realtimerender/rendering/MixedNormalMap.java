package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class MixedNormalMap implements NormalMap {

	private final NormalMap a;
	private final NormalMap b;
	private final double mix;
	
	public MixedNormalMap(NormalMap a, NormalMap b, double mix) {
		this.a = a;
		this.b = b;
		this.mix = mix;
	}
	
	@Override
	public Float64Vector getNormal(Coordinate point) {
		Float64Vector n_a = a.getNormal(point);
		Float64Vector n_b = b.getNormal(point);
		if ((n_a == null) || (n_b == null)) {
			return null;
		}
		
		return n_a.times(mix).plus(n_b.times(1 - mix));
	}

}
