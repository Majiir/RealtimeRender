package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public class AdaptiveNormalMap implements NormalMap {

	private final NormalMap approximate;
	private final NormalMap precise;
	
	public AdaptiveNormalMap(NormalMap approximate, NormalMap precise) {
		this.approximate = approximate;
		this.precise = precise;
	}
	
	@Override
	public Float64Vector getNormal(Coordinate point) {
		Float64Vector n_a = approximate.getNormal(point);
		Float64Vector n_p = precise.getNormal(point);
		if ((n_a == null) || (n_p == null)) {
			return null;
		}
		Float64Vector up = Float64Vector.valueOf(0, 0, 1);
		double avgslope = Math.tan(Math.acos(n_a.times(up).divide(n_a.norm().times(up.norm())).doubleValue()));
		
		//avgslope = Math.min(0, avgslope - 0.1); // cutoff to make smooth stuff really smooth
		//avgslope *= 0.75;
		
		return n_a.plus(n_p.times(avgslope)).times(1 / (avgslope + 1));
	}

}
