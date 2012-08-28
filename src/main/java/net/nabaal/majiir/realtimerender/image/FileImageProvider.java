package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.RealtimeRender;


public class FileImageProvider extends ImageProvider {

	private final FilePattern pattern;
	private final Set<File> changed = new HashSet<File>();

	public FileImageProvider(FilePattern pattern) {
		this.pattern = pattern;
	}
	
	public Set<File> getChanged() {
		return new HashSet<File>(changed);
	}

	@Override
	public BufferedImage getImage(Coordinate tile) {
		try {
			return ImageIO.read(pattern.getFile(tile));
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			RealtimeRender.getPluginLogger().warning("RealtimeRender: error reading tile: " + tile);
			return null;
		}
	}

	@Override
	public void removeImage(Coordinate tile) {
		File file = pattern.getFile(tile);
		file.delete();
		changed.add(file); // TODO: Make the commit provider understand that this means deletion... or something.
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		try {
			File file = pattern.getFile(tile);
			ImageIO.write(image, "png", file);
			changed.add(file);
		} catch (Exception e) {
			RealtimeRender.getPluginLogger().warning("RealtimeRender: error saving tile: " + tile);
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<Coordinate> getTiles() {
		File[] files = pattern.getParent().listFiles(pattern);
		Set<Coordinate> tiles = new HashSet<Coordinate>();
		for (File file : files) {
			tiles.add(pattern.getTile(file));
		}
		return tiles;
	}

}