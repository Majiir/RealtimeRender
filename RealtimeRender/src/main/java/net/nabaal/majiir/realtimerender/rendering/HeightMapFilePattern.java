package net.nabaal.majiir.realtimerender.rendering;

import java.io.File;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.FilePattern;

public class HeightMapFilePattern implements FilePattern {

	private final File directory;
	private final String prefix;
	
	public HeightMapFilePattern(File directory, String prefix) {
		this.directory = directory;
		this.prefix = prefix;
	}
	
	@Override
	public File getFile(Coordinate tile) {
		return new File(directory, String.format("%s.y.%d.%d.png", prefix, tile.getX(), tile.getY()));
	}

	@Override
	public boolean accept(File pathname) {
		// TODO
		throw new UnsupportedOperationException("File filter not implemented.");
	}
	
	@Override
	public Coordinate getTile(File file) {
		// TODO
		throw new UnsupportedOperationException("Pattern matching not implemented.");
	}
	
}
