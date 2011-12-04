package net.nabaal.majiir.realtimerender.rendering;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.ChunkSnapshot;

import net.nabaal.majiir.realtimerender.RenderChunkTask;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;
import net.nabaal.majiir.realtimerender.image.Coordinate;

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
		// TODO: Multithreading
		for (ChunkSnapshot snapshot : snapshots.values()) {
			chunkRenderer.render(snapshot);
			new RenderChunkTask(chunkRenderer, snapshot).run();
		}
	}
	
	public void endBatch() {
		snapshots = null;
	}
	
}
