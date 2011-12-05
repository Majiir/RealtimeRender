package net.nabaal.majiir.realtimerender.rendering;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;

public class HeightMapReadCache extends HeightMapChunkProvider {

	private final ConcurrentMap<Coordinate, HeightMapChunk> chunks = new ConcurrentHashMap<Coordinate, HeightMapChunk>();
	private final HeightMapChunkProvider source;
	
	public HeightMapReadCache(HeightMapChunkProvider source) {
		this.source = source;
	}
	
	public void clear() {
		chunks.clear();
	}

	@Override
	protected HeightMapChunk getHeightMapChunk(Coordinate chunkLocation) {
		if (chunks.containsKey(chunkLocation)) {
			return chunks.get(chunkLocation);
		}
		HeightMapChunk chunk = source.getHeightMapChunk(chunkLocation);
		if (chunk != null) {
			chunks.put(chunkLocation, chunk);
		}
		return chunk;
	}

	@Override
	protected void setHeightMapChunk(Coordinate chunkLocation, HeightMapChunk chunk) {
		if (chunk != null) {
			chunks.put(chunkLocation, chunk);
		} else {
			chunks.remove(chunkLocation);
		}
		source.setHeightMapChunk(chunkLocation, chunk);
	}

}