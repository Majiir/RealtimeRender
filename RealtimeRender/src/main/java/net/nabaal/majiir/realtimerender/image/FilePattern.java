package net.nabaal.majiir.realtimerender.image;

import java.io.File;
import java.io.FileFilter;


public interface FilePattern extends FileFilter {

	public File getFile(Coordinate tile);

	public Coordinate getTile(File file);
	
	@Override
	public boolean accept(File pathname);

}