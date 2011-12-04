package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.nabaal.majiir.realtimerender.image.Coordinate;
import net.nabaal.majiir.realtimerender.image.ImageProvider;

public class ImageHeightMap implements HeightMap {

	private final ImageProvider imageProvider;
	
	public ImageHeightMap(ImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}

	@Override
	public int getHeight(Coordinate point) {
		Color color = imageProvider.getPixelColor(point, Coordinate.OFFSET_BLOCK_CHUNK);
		int height = color.getBlue();
		if ((height > 127) || (color.getAlpha() == 0)) { // TODO: Better constants and valid-height detection
			return NO_HEIGHT_INFORMATION;
		}
		return height;
	}

	@Override
	public void setHeight(Coordinate point, int height) {
		
		// TODO: Make not retarded
		throw new UnsupportedOperationException();
		/*
		Color color;
		if (height == NO_HEIGHT_INFORMATION) {
			color = new Color(0, 0, 128);
		} else {
			color = new Color(0, 0, height);
		}

		imageProvider.setPixelColor(point, color, Coordinate.OFFSET_BLOCK_CHUNK);
		*/
	}
	
	public void setChunk(Coordinate chunk, HeightMapChunk hmc) {
		
		BufferedImage image = ImageProvider.createImage(Coordinate.SIZE_CHUNK);
		Graphics2D graphics = image.createGraphics();
		
		// TODO: Enumerate over coordinates
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				
				int height = hmc.getHeight(chunk.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
				
				Color color;
				if (height == NO_HEIGHT_INFORMATION) {
					color = new Color(0, 0, 128);
				} else {
					color = new Color(0, 0, height);
				}
				
				graphics.setColor(color);
				graphics.fillRect(x, y, 1, 1);
				
			}
		}
		
		imageProvider.setImage(chunk, image);
		
	}
	
}
