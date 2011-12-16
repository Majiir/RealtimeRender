package net.nabaal.majiir.realtimerender.rendering;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ReadCache;

public class NormalMapReadCache extends NormalMapProvider implements ReadCache {

	private final NormalMap source;
	private final ConcurrentMap<Coordinate, NormalMapTile> cache = new ConcurrentHashMap<Coordinate, NormalMapTile>();
	
	public NormalMapReadCache(NormalMap source) {
		this.source = source;
	}
	
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

	@Override
	public void clear() {
		cache.clear();
	}
	
}
