package net.nabaal.majiir.realtimerender;

import org.bukkit.Location;

public class MapOptions {
	
	private final int minZoom;
	private final int maxZoom;
	private final Coordinate spawn;
	
	public MapOptions(int minZoom, int maxZoom, Location spawn) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.spawn = new Coordinate(spawn.getBlockX(), spawn.getBlockY(), Coordinate.LEVEL_BLOCK);
	}

	public int getMinZoom() {
		return minZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public Coordinate getSpawn() {
		return spawn;
	}
	
}
