package net.nabaal.majiir.realtimerender.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Set;

import net.nabaal.majiir.realtimerender.Coordinate;


public class ZoomImageBuilder extends ImageProvider {

	private final ImageProvider source;
	private final int zoom;
	
	public ZoomImageBuilder(ImageProvider source, int zoom) {
		this.source = source;
		this.zoom = zoom;
	}
	
	@Override
	public BufferedImage getImage(Coordinate tile) {
		return getZoomImage(source.getImage(tile));
	}

	@Override
	public void setImage(Coordinate tile, BufferedImage image) {
		source.setImage(tile, getZoomImage(image));
	}

	private BufferedImage getZoomImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		// TODO: Better clarity here
		if (zoom < 0) {
			width <<= (zoom * -1);
			height <<= (zoom * -1);
		} else {
			width >>= zoom;
			height >>= zoom;
		}
		
		BufferedImage newImg = createImage(width, height);
		Graphics2D graphics = newImg.createGraphics();
		
		Object hint;
		if (zoom < 0) {
			hint = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		} else {
			hint = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
		}
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		
		graphics.drawImage(image, 0, 0, width, height, null);
		return newImg;
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
