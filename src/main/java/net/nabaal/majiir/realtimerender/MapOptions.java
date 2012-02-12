package net.nabaal.majiir.realtimerender;

import org.bukkit.Location;
import org.bukkit.World;

public class MapOptions {
	
	private final int minZoom;
	private final int maxZoom;
	private final Coordinate spawn;
	
	public MapOptions(int minZoom, int maxZoom, World world) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		Location spawn = world.getSpawnLocation();
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
