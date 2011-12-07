package net.nabaal.majiir.realtimerender.rendering;

import org.jscience.mathematics.vector.Float64Vector;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class NormalMapProvider implements NormalMap {

	public abstract NormalMapTile getNormalMapTile(Coordinate tile);
	
	@Override
	public final Float64Vector getNormal(Coordinate block) {
		return this.getNormalMapTile(block.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK)).getNormal(block);
	}

}
