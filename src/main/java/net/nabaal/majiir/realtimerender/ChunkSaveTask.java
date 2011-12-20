package net.nabaal.majiir.realtimerender;

import net.nabaal.majiir.realtimerender.rendering.ChunkManager;

public class ChunkSaveTask implements Runnable {
	
	private boolean stop = false;
	private final ChunkManager manager;
	
	public ChunkSaveTask(ChunkManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		while (!stop) {
			manager.doSaveChunk();
		}
	}
	
	public void stop() {
		stop = true;
	}

}
