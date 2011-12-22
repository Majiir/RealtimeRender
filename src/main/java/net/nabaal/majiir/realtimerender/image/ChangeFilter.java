package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.Set;

import net.nabaal.majiir.realtimerender.Coordinate;


public class ChangeFilter extends ImageProvider {

	private final ImageProvider source;
	
	public ChangeFilter(ImageProvider source) {
		this.source = source;
	}
	
	@Override
	public BufferedImage getImage(Coordinate tile) {
		return source.getImage(tile);
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		if (!imageCompare(image, source.getImage(tile))) {
			source.setImage(tile, image);
		}
	}
	
	private static boolean imageCompare(BufferedImage a, BufferedImage b) {
		if (a == b) { return true; }
		if ((a == null) || (b == null)) { return false; }
		if (a.getWidth() != b.getWidth()) { return false; }
		if (a.getHeight() != b.getHeight()) { return false; }
		for (int x = 0; x < a.getWidth(); x++) {
			for (int y = 0; y < a.getHeight(); y++) {
				if (a.getRGB(x, y) != b.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
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
