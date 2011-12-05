package net.nabaal.majiir.realtimerender.rendering;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;

public class CachedNormalMap extends NormalMapProvider {

	private final NormalMap source;
	private final ConcurrentMap<Coordinate, NormalMapTile> cache = new ConcurrentHashMap<Coordinate, NormalMapTile>();
	
	public CachedNormalMap(NormalMap source) {
		this.source = source;
	}

	// TODO: Make caches into pass-throughs, with the cache provider a separate class (memory cache, image-based cache, whatever)
	// (also make height/normal maps two-way so heightmap rendering can be done directly onto them, committed back from caches, etc)
	
	@Override
	public NormalMapTile getNormalMapTile(Coordinate tile) {
		NormalMapTile result = cache.get(tile);
		if (result == null) {
			if (source instanceof NormalMapProvider) {
				result = ((NormalMapProvider)source).getNormalMapTile(tile);
			} else {
				result = new NormalMapTile(tile, source);
			}
			cache.put(tile, result);
		}
		return result;
	}
	
}
