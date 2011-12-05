package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.nabaal.majiir.realtimerender.Coordinate;


// TODO: Size-check all operations.
public class ImageWriteCache extends ImageProvider {

	private final ConcurrentMap<Coordinate, BufferedImage> images = new ConcurrentHashMap<Coordinate, BufferedImage>();
	private final ImageProvider source;
	
	public ImageWriteCache(ImageProvider source) {
		this.source = source;
	}
	
	@Override
	public BufferedImage getImage(Coordinate tile) {
		BufferedImage image = images.get(tile);
		if (image == null) {
			image = source.getImage(tile);
		}
		return image;
	}

	@Override
	public void removeImage(Coordinate tile) {
		// TODO: This whole method (from ImageProvider) can be deprecated.
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		images.put(tile, image);
	}
	
	@Override
	public Set<Coordinate> getTiles() {
		Set<Coordinate> tiles = new HashSet<Coordinate>();
		tiles.addAll(source.getTiles());
		tiles.addAll(images.keySet());
		return tiles;
	}
	
	public void commit() {
		// TODO: Cleaner (more thread-safe/atomic) method
		for (Entry<Coordinate, BufferedImage> entry : images.entrySet()) {
			source.setImage(entry.getKey(), entry.getValue());
		}
		images.clear();
	}

}