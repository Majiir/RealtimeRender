package net.nabaal.majiir.realtimerender.rendering;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.WriteCache;

public class HeightMapWriteCache extends HeightMapChunkProvider implements WriteCache {

	private final ConcurrentMap<Coordinate, HeightMapChunk> chunks = new ConcurrentHashMap<Coordinate, HeightMapChunk>();
	private final HeightMapChunkProvider source;
	
	public HeightMapWriteCache(HeightMapChunkProvider source) {
		this.source = source;
	}
	
	@Override
	public void commit() {
		for (Map.Entry<Coordinate, HeightMapChunk> entry : chunks.entrySet()) {
			source.setHeightMapChunk(entry.getKey(), entry.getValue());
		}
	}

	@Override
	protected HeightMapChunk getHeightMapChunk(Coordinate chunkLocation) {
		if (chunks.containsKey(chunkLocation)) {
			return chunks.get(chunkLocation);
		}
		return source.getHeightMapChunk(chunkLocation);
	}

	@Override
	protected void setHeightMapChunk(Coordinate chunkLocation, HeightMapChunk chunk) {
		if (chunk == null) {
			throw new IllegalArgumentException("HeightMapChunk cannot be null.");
		}
		chunks.put(chunkLocation, chunk);
	}

}