package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.Set;


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
		
		boolean changed = false;
		
		BufferedImage original = source.getImage(tile);
		
		if (original != null) {
			
			int width = original.getWidth();
			int height = original.getHeight();
			
			if (width != image.getWidth()) {
				changed = true;
			}
			
			if (!changed && (height != image.getHeight())) {
				changed = true;
			}
			
			for (int x = 0; x < width; x++) {
				for (int y = 0; (y < height) && !changed; y++) {
					int oldPixel = original.getRGB(x, y);
					int newPixel = image.getRGB(x, y);
					if (oldPixel != newPixel) {
						changed = true;
					}
				}
			}
			
		} else {
			changed = true; // TODO: Clean this whole routine up
		}
		
		if (changed) {
			source.setImage(tile, image);
		}
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
