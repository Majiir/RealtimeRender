package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

public abstract class HeightMapChunkProvider implements HeightMap {

	protected abstract HeightMapChunk getHeightMapChunk(Coordinate chunkLocation);
	
	protected abstract void setHeightMapChunk(Coordinate chunkLocation, HeightMapChunk chunk);
	
	@Override
	public final int getHeight(Coordinate point) {
		return this.getHeightMapChunk(point.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK)).getHeight(point);
	}
	
	@Override
	public final void setHeight(Coordinate block, int height) {
		Coordinate chunkLocation = block.zoomOut(Coordinate.OFFSET_BLOCK_CHUNK);
		HeightMapChunk chunk = getHeightMapChunk(chunkLocation);
		chunk.setHeight(block, height);
		setHeightMapChunk(chunkLocation, chunk);
	}

}
