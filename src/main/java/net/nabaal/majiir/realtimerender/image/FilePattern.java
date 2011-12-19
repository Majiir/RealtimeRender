package net.nabaal.majiir.realtimerender.image;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import net.nabaal.majiir.realtimerender.Coordinate;


public abstract class FilePattern implements FileFilter {

	private final File parent;
	
	public FilePattern(File parent) {
		if (!parent.isDirectory()) {
			throw new IllegalArgumentException("Parent must be a directory");
		}
		this.parent = parent;
	}
	
	public abstract File getFile(Coordinate tile);

	public abstract Coordinate getTile(File file);
	
	public final File getParent() {
		return this.parent;
	}
	
	public final List<Coordinate> getTiles() {
		List<Coordinate> tiles = new ArrayList<Coordinate>();
		File[] files = parent.listFiles(this);
		for (File file : files) {
			tiles.add(getTile(file));
		}
		return tiles;
	}
	
	@Override
	public final boolean accept(File file) {
		return getTile(file) != null;
	}

}