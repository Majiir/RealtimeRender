package net.nabaal.majiir.realtimerender.rendering;

import java.io.File;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.FilePattern;

//TODO: Rename
public class TileFilePattern implements FilePattern {

	private final File directory;
	private final String prefix;
	
	public TileFilePattern(File directory, String prefix) {
		this.directory = directory;
		this.prefix = prefix;
	}
	
	@Override
	public File getFile(Coordinate tile) {
		return new File(directory, String.format("%s.%d.%d.%d.png", prefix, tile.getLevel(), tile.getX(), tile.getY()));
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
