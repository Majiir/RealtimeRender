package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

import org.jscience.mathematics.vector.Float64Vector;

public interface NormalMap {

	/**
	 * Computes the normal vector at the given point.
	 * @param point Point to compute the normal at.
	 * @return Normal vector at the point, or null if invalid height information exists there.
	 */
	public Float64Vector getNormal(Coordinate point);
	
}
