package net.nabaal.majiir.realtimerender.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.RealtimeRender;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

public class ChunkManager {
	
	private final BlockingQueue<ChunkSnapshot> incoming = new ArrayBlockingQueue<ChunkSnapshot>(200);
	private final ExecutorService executor;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final ChunkPreprocessor processor;
	private final FileChunkSnapshotProvider provider;
	private final SortedSet<Coordinate> chunks = new TreeSet<Coordinate>();
	
	public ChunkManager(ChunkPreprocessor processor, FileChunkSnapshotProvider provider, int idleThreads, int maxThreads, int queueSize) {
		this.processor = processor;
		this.provider = provider;
		this.executor = new ThreadPoolExecutor(idleThreads, maxThreads, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public Lock getLock() {
		return lock.readLock();
	}
	
	public void enqueue(Chunk chunk) {
		try {
			incoming.put(chunk.getChunkSnapshot(true, true, true));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doSaveChunk() {
		lock.readLock().lock();
		try {
			ChunkSnapshot chunk = incoming.poll(10, TimeUnit.MILLISECONDS);
			if (chunk != null) {
				SerializableChunkSnapshot snapshot = processor.processChunk(chunk);
				provider.setSnapshot(snapshot);
			}
		} catch (InterruptedException e) {
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public void startBatch() {
		lock.writeLock().lock();
		chunks.addAll(provider.getTiles());
	}
	
	public void render(final ChunkRenderer chunkRenderer) {
			List<Future<?>> futures = new ArrayList<Future<?>>();
			for (Coordinate chunk : chunks) {
					final ChunkSnapshot snapshot = provider.getSnapshot(chunk);
					if (snapshot != null) {
						futures.add(executor.submit(new Runnable() {
							@Override
							public void run() {
								chunkRenderer.render(snapshot);
							}
						}));
					}
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
		for (Coordinate chunk : chunks) {
			if (!provider.getPattern().getFile(chunk).delete()) {
				RealtimeRender.getPluginLogger().warning("RealtimeRender: could not delete file for chunk: " + chunk);
			}
		}
		chunks.clear();
		lock.writeLock().unlock();
	}
	
}
