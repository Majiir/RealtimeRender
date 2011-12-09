package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class HeightMapProvider extends HeightMap {

	protected abstract HeightMapTile getHeightMapChunk(Coordinate chunkLocation);
	
	protected abstract void setHeightMapChunk(Coordinate chunkLocation, HeightMapTile chunk);
	
	@Override
	public final byte getHeight(Coordinate point) {
		return this.getHeightMapChunk(point.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK)).getHeight(point);
	}
	
	@Override
	public final void setHeight(Coordinate block, byte height) {
		Coordinate chunkLocation = block.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK);
		HeightMapTile chunk = getHeightMapChunk(chunkLocation);
		chunk.setHeight(block, height);
		setHeightMapChunk(chunkLocation, chunk);
	}

}
