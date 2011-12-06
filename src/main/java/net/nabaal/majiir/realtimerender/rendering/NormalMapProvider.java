package net.nabaal.majiir.realtimerender.rendering;

import org.jscience.mathematics.vector.Float64Vector;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class NormalMapProvider implements NormalMap {

	public abstract NormalMapTile getNormalMapTile(Coordinate tile);
	
	@Override
	public final Float64Vector getNormal(Coordinate point) {
		return this.getNormalMapTile(point.zoomOut(4)).getNormal(point);
	}

}
