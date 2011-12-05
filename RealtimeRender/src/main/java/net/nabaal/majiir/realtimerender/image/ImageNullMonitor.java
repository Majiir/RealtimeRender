package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.Set;

import net.nabaal.majiir.realtimerender.Coordinate;


public class ImageNullMonitor extends ImageProvider {

	private final ImageProvider source;
	private final int size;
	
	public ImageNullMonitor(ImageProvider source, int size) {
		this.source = source;
		this.size = size;
	}

	@Override
	public BufferedImage getImage(Coordinate tile) {
		BufferedImage image = source.getImage(tile);
		if (image != null) {
			return image;
		}
		return new BufferedImage(1 << size, 1 << size, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		// TODO: Consider writing a black/transparent/configurable image
		source.setImage(tile, image);
	}

	@Override
	public void removeImage(Coordinate tile) {
		source.removeImage(tile);
	}

	@Override
	public Set<Coordinate> getTiles() {
		return source.getTiles();
	}

}
