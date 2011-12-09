package net.nabaal.majiir.realtimerender.rendering;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ReadCache;

public class HeightMapReadCache extends HeightMapChunkProvider implements ReadCache {

	private final ConcurrentMap<Coordinate, HeightMapTile> chunks = new ConcurrentHashMap<Coordinate, HeightMapTile>();
	private final HeightMap source;
	
	public HeightMapReadCache(HeightMap source) {
		this.source = source;
	}
	
	@Override
	public void clear() {
		chunks.clear();
	}

	@Override
	protected HeightMapTile getHeightMapChunk(Coordinate chunkLocation) {
		if (chunks.containsKey(chunkLocation)) {
			return chunks.get(chunkLocation);
		}
		
		HeightMapTile chunk;
		if (source instanceof HeightMapChunkProvider) {
			chunk = ((HeightMapChunkProvider)source).getHeightMapChunk(chunkLocation);
		} else {
			chunk = new HeightMapTile(chunkLocation, source);
		}
		
		if (chunk != null) {
			chunks.put(chunkLocation, chunk);
		}
		return chunk;
	}

	@Override
	protected void setHeightMapChunk(Coordinate chunkLocation, HeightMapTile chunk) {
		if (chunk != null) {
			chunks.put(chunkLocation, chunk);
		} else {
			chunks.remove(chunkLocation);
		}
		
		if (source instanceof HeightMapChunkProvider) {
			((HeightMapChunkProvider)source).setHeightMapChunk(chunkLocation, chunk);
		} else {
			// TODO: Cleaner
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 16; y++) {
					Coordinate block = chunkLocation.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK));
					source.setHeight(block, chunk.getHeight(block));
				}
			}
		}
	}

}