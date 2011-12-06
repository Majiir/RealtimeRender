package net.nabaal.majiir.realtimerender;

import org.bukkit.ChunkSnapshot;

import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

public class RenderChunkTask implements Runnable {

	private final ChunkRenderer renderer;
	private final ChunkSnapshot snapshot;
	
	public RenderChunkTask(ChunkRenderer renderer, ChunkSnapshot snapshot) {
		this.renderer = renderer;
		this.snapshot = snapshot;
	}

	@Override
	public void run() {
		renderer.render(snapshot);
	}

}
