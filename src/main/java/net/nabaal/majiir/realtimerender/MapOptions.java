package net.nabaal.majiir.realtimerender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class MapOptions {
	
	private final int minZoom;
	private final int maxZoom;
	private final List<MarkerGroup> markerGroups = new ArrayList<MarkerGroup>();
	private final Coordinate spawn;
	
	public MapOptions(int minZoom, int maxZoom, World world, List<MarkerGroup> markerGroups) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.markerGroups.addAll(markerGroups);
		Location spawn = world.getSpawnLocation();
		this.spawn = new Coordinate(spawn.getBlockX(), spawn.getBlockY(), Coordinate.LEVEL_BLOCK);
	}

	public int getMinZoom() {
		return minZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}
	
	public List<MarkerGroup> getMarkerGroups() {
		return Collections.unmodifiableList(markerGroups);
	}

	public Coordinate getSpawn() {
		return spawn;
	}
	
}
