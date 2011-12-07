package net.nabaal.majiir.realtimerender.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.bukkit.ChunkSnapshot;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.RealtimeRender;
import net.nabaal.majiir.realtimerender.RenderChunkTask;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

public class ChunkManager {
	
	private final ConcurrentLinkedQueue<ChunkSnapshot> incoming = new ConcurrentLinkedQueue<ChunkSnapshot>();
	private ConcurrentMap<Coordinate, ChunkSnapshot> snapshots; 
	
	public void enqueue(ChunkSnapshot chunkSnapshot) {
		incoming.add(chunkSnapshot);
	}
	
	public void startBatch() {
		snapshots = new ConcurrentHashMap<Coordinate, ChunkSnapshot>();
		while (!incoming.isEmpty()) {
			ChunkSnapshot chunkSnapshot = incoming.remove();
			snapshots.put(Coordinate.fromSnapshot(chunkSnapshot), chunkSnapshot);
		}
	}
	
	public void render(ChunkRenderer chunkRenderer) {
		ExecutorService executor = RealtimeRender.getExecutor();
		List<Future<?>> futures = new ArrayList<Future<?>>();
		for (ChunkSnapshot snapshot : snapshots.values()) {
			futures.add(executor.submit(new RenderChunkTask(chunkRenderer, snapshot)));
		}
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void endBatch() {
		snapshots = null;
	}
	
}
