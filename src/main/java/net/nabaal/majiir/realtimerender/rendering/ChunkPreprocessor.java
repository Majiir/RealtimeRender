package net.nabaal.majiir.realtimerender.rendering;

import org.bukkit.ChunkSnapshot;

public interface ChunkPreprocessor {
	
	public SerializableChunkSnapshot processChunk(ChunkSnapshot chunk);
	
}
