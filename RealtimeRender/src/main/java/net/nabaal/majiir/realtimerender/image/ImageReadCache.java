package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;


// TODO: Size-check all operations.
public class ImageReadCache extends ImageProvider {

	private final ConcurrentMap<Coordinate, BufferedImage> images = new ConcurrentHashMap<Coordinate, BufferedImage>();
	private final ImageProvider source;
	
	public ImageReadCache(ImageProvider source) {
		this.source = source;
	}
	
	@Override
	public BufferedImage getImage(Coordinate tile) {
		BufferedImage image = images.get(tile);
		if (image == null) {
			image = source.getImage(tile);
			if (image != null) {
				images.put(tile, image);
			}
		}
		return image;
	}

	@Override
	public void removeImage(Coordinate tile) {
		images.remove(tile);
		source.removeImage(tile);
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		images.put(tile, image);
		source.setImage(tile, image);
	}
	
	@Override
	public Set<Coordinate> getTiles() {
		Set<Coordinate> tiles = new HashSet<Coordinate>();
		tiles.addAll(source.getTiles());
		tiles.addAll(images.keySet());
		return tiles;
	}
	
	public void clear() {
		images.clear();
	}

}