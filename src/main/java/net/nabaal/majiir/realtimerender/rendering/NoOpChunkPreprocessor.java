package net.nabaal.majiir.realtimerender.rendering;

import org.bukkit.ChunkSnapshot;

public class NoOpChunkPreprocessor implements ChunkPreprocessor {

	@Override
	public SerializableChunkSnapshot processChunk(ChunkSnapshot chunk) {
		return new ProcessedChunkSnapshot(chunk);
	}

}
