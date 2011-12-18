package net.nabaal.majiir.realtimerender.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bukkit.ChunkSnapshot;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.RenderChunkTask;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

public class ChunkManager {
	
	private final ConcurrentLinkedQueue<ChunkSnapshot> incoming = new ConcurrentLinkedQueue<ChunkSnapshot>();
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private ConcurrentMap<Coordinate, ChunkSnapshot> snapshots;
	private final ChunkPreprocessor processor;
	
	public ChunkManager(ChunkPreprocessor processor) {
		this.processor = processor;
	}
	
	public void enqueue(ChunkSnapshot chunkSnapshot) {
		incoming.add(processor.processChunk(chunkSnapshot));
	}
	
	public void startBatch() {
		snapshots = new ConcurrentHashMap<Coordinate, ChunkSnapshot>();
		while (!incoming.isEmpty()) {
			ChunkSnapshot chunkSnapshot = incoming.remove();
			snapshots.put(Coordinate.fromSnapshot(chunkSnapshot), chunkSnapshot);
		}
	}
	
	public void render(ChunkRenderer chunkRenderer) { 
		List<Future<?>> futures = new ArrayList<Future<?>>();
		for (ChunkSnapshot snapshot : snapshots.values()) {
			futures.add(executor.submit(new RenderChunkTask(chunkRenderer, snapshot)));
		}
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Exception should be here! #1"); // TODO: Test
			} catch (ExecutionException e) {
				e.printStackTrace();
				System.out.println("Exception should be here! #2");
			}
		}
	}
	
	public void endBatch() {
		snapshots = null;
	}
	
}
