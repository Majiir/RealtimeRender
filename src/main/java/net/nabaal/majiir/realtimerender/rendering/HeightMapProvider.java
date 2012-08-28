package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class HeightMapProvider extends HeightMap {

	private final int size;
	
	public HeightMapProvider(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	protected abstract HeightMapTile getHeightMapTile(Coordinate tileLocation);
	
	protected abstract void setHeightMapTile(Coordinate tileLocation, HeightMapTile tile);
	
	@Override
	public final int getHeight(Coordinate point) {
		return this.getHeightMapTile(point.zoomOut(size)).getHeight(point);
	}
	
	@Override
	public final void setHeight(Coordinate block, int height) {
		Coordinate tileLocation = block.zoomOut(size);
		HeightMapTile tile = getHeightMapTile(tileLocation);
		tile.setHeight(block, height);
		setHeightMapTile(tileLocation, tile);
	}

}
