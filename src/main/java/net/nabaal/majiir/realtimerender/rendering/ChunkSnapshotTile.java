package net.nabaal.majiir.realtimerender.rendering;

import java.io.Serializable;

import net.nabaal.majiir.realtimerender.Coordinate;

public class ChunkSnapshotTile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Coordinate location;
	private final ProcessedChunkSnapshot[] snapshots;
	private final transient int offset;
	
	public ChunkSnapshotTile(Coordinate location) {
		if (location.getLevel() < Coordinate.LEVEL_CHUNK) {
			throw new IllegalArgumentException("Snapshot tile may not be smaller than a chunk");
		}
		this.location = location;
		this.offset = location.getLevel() - Coordinate.LEVEL_CHUNK;
		this.snapshots = new ProcessedChunkSnapshot[1 << offset];
	}
	
	public ProcessedChunkSnapshot getSnapshot(Coordinate chunk) {
		return snapshots[getArrayIndex(chunk)];		
	}
	
	public void setSnapshot(Coordinate chunk, ProcessedChunkSnapshot snapshot) {
		snapshots[getArrayIndex(chunk)] = snapshot;
	}
	
	private int getArrayIndex(Coordinate chunk) {
		if (chunk.getLevel() != Coordinate.LEVEL_CHUNK) {
			throw new IllegalArgumentException("The given Coordinate is not a chunk");
		}
		if (!chunk.zoomOut(offset).equals(location)) {
			throw new IllegalArgumentException("The tile does not contain that chunk");
		}
		chunk = chunk.subCoordinate(offset);
		return getArrayIndex(chunk.getX(), chunk.getY());
	}
	
	private int getArrayIndex(int x, int y) {
		return (x << offset) + y;
	}
	
}
