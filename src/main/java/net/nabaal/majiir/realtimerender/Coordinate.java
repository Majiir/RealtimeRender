package net.nabaal.majiir.realtimerender;

import java.io.Serializable;

import org.bukkit.ChunkSnapshot;

public final class Coordinate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int LEVEL_BLOCK = -8;
	public static final int LEVEL_CHUNK = -4;
	public static final int LEVEL_TILE = 0;
	public static final int OFFSET_BLOCK_CHUNK = LEVEL_CHUNK - LEVEL_BLOCK;
	public static final int OFFSET_CHUNK_TILE = LEVEL_TILE - LEVEL_CHUNK;
	public static final int SIZE_BLOCK = 0;
	public static final int SIZE_CHUNK = 4;
	public static final int SIZE_TILE = 8;
	
	private int x;
	private int y;
	private int level;
	
	public Coordinate(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getLevel() {
		return level;
	}
	
	private void checkLevel(Coordinate other) {
		if (getLevel() != other.getLevel()) {
			throw new IllegalArgumentException("Coordinate levels must match.");
		}
	}
	
	public Coordinate plus(Coordinate a) {
		checkLevel(a);
		return new Coordinate(getX() + a.getX(), getY() + a.getY(), getLevel());
	}
	
	public Coordinate minus(Coordinate a) {
		checkLevel(a);
		return new Coordinate(getX() - a.getX(), getY() - a.getY(), getLevel());
	}
	
	public Coordinate zoomIn(int zoom) {
		return new Coordinate(getX() << zoom, getY() << zoom, getLevel() - zoom);
	}
	
	public Coordinate zoomOut(int zoom) {
		return new Coordinate(getX() >> zoom, getY() >> zoom, getLevel() + zoom);
	}
	
	public Coordinate subOrigin(int zoom) {
		return this.zoomOut(zoom).zoomIn(zoom);
	}
	
	public Coordinate subCoordinate(int zoom) {
		return this.minus(this.subOrigin(zoom));
	}
	
	public static Coordinate fromSnapshot(ChunkSnapshot snapshot) {
		return new Coordinate(snapshot.getX(), snapshot.getZ(), LEVEL_CHUNK);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d) [%d]", this.getX(), this.getY(), this.getLevel());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Coordinate)) { return false; }
		Coordinate other = (Coordinate) obj;
		if (level != other.level) { return false; }
		if (x != other.x) { return false; }
		if (y != other.y) { return false; }
		return true;
	}

}