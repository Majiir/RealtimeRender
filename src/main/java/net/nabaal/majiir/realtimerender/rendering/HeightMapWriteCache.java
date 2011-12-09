package net.nabaal.majiir.realtimerender.rendering;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.WriteCache;

public class HeightMapWriteCache extends HeightMapProvider implements WriteCache {

	private final ConcurrentMap<Coordinate, HeightMapTile> chunks = new ConcurrentHashMap<Coordinate, HeightMapTile>();
	private final HeightMap source;
	
	public HeightMapWriteCache(HeightMap source) {
		this.source = source;
	}
	
	@Override
	public void commit() {
		for (Map.Entry<Coordinate, HeightMapTile> entry : chunks.entrySet()) {
			if (source instanceof HeightMapProvider) {
				((HeightMapProvider)source).setHeightMapChunk(entry.getKey(), entry.getValue());
			} else {
				// TODO: Cleaner
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < 16; y++) {
						Coordinate block = entry.getKey().zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK));
						source.setHeight(block, entry.getValue().getHeight(block));
					}
				}
			}
		}
		chunks.clear();
	}

	@Override
	protected HeightMapTile getHeightMapChunk(Coordinate chunkLocation) {
		if (chunks.containsKey(chunkLocation)) {
			return chunks.get(chunkLocation);
		}
		
		if (source instanceof HeightMapProvider) {
			return ((HeightMapProvider)source).getHeightMapChunk(chunkLocation);
		} else {
			return new HeightMapTile(chunkLocation, source);
		}
	}

	@Override
	protected void setHeightMapChunk(Coordinate chunkLocation, HeightMapTile chunk) {
		if (chunk == null) {
			throw new IllegalArgumentException("HeightMapChunk cannot be null.");
		}
		chunks.put(chunkLocation, chunk);
	}

}