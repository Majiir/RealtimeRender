package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

import org.bukkit.ChunkSnapshot;

public class HeightMapChunkPreprocessor implements ChunkPreprocessor {

	private final ChunkRenderer renderer;
	
	public HeightMapChunkPreprocessor(HeightMap heightMap) {
		renderer = new HeightMapRenderer(heightMap);
	}
	
	@Override
	public SerializableChunkSnapshot processChunk(ChunkSnapshot chunk) {
		renderer.render(chunk);
		return new ProcessedChunkSnapshot(chunk);
	}

}
