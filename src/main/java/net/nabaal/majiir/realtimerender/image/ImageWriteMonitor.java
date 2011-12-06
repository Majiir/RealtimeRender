package net.nabaal.majiir.realtimerender.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.nabaal.majiir.realtimerender.Coordinate;


public class ImageWriteMonitor extends ImageProvider {

	private final ImageProvider source;
	private final List<ImageProvider> listeners = new ArrayList<ImageProvider>();
	
	public ImageWriteMonitor(ImageProvider source) {
		this.source = source;
	}
	
	public void addListener(ImageProvider listener) {
		listeners.add(listener);
	}
	
	@Override
	public BufferedImage getImage(Coordinate tile) {
		return source.getImage(tile);
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		source.setImage(tile, image);
		for (ImageProvider listener : listeners) {
			listener.setImage(tile, image);
		}
	}

	@Override
	public void removeImage(Coordinate tile) {
		source.removeImage(tile);
		for (ImageProvider listener : listeners) {
			listener.removeImage(tile);
		}
	}

	@Override
	public Set<Coordinate> getTiles() {
		return source.getTiles();
	}

}
