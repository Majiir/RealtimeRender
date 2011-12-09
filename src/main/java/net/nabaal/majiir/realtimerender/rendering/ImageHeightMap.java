package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ImageProvider;

public class ImageHeightMap extends HeightMapChunkProvider {

	private final ImageProvider imageProvider;
	
	public ImageHeightMap(ImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}
	
	@Override
	protected HeightMapTile getHeightMapChunk(Coordinate chunkLocation) {
		
		BufferedImage image = imageProvider.getImage(chunkLocation);
		HeightMapTile chunk = new HeightMapTile(chunkLocation);
		
		if (image == null) {
			return chunk;
		}
		
		// TODO: Enumerate over coordinates
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				Color color = new Color(image.getRGB(x, y));
				byte height = (byte) (color.getBlue() - 128);
				if (!HeightMap.isValid(height)) { // TODO: Better constants and valid-height detection
					height = NO_HEIGHT_INFORMATION;
				}
				chunk.setHeight(chunkLocation.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)), height);
			}
		}
		
		return chunk;
		
	}
	
	@Override
	protected void setHeightMapChunk(Coordinate chunkLocation, HeightMapTile chunk) {
		
		BufferedImage image = ImageProvider.createImage(Coordinate.SIZE_CHUNK);
		Graphics2D graphics = image.createGraphics();
		
		// TODO: Enumerate over coordinates
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				byte height = chunk.getHeight(chunkLocation.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, y, Coordinate.LEVEL_BLOCK)));
				Color color = new Color(0, 0, height + 128);
				graphics.setColor(color);
				graphics.fillRect(x, y, 1, 1);
			}
		}
		
		imageProvider.setImage(chunkLocation, image);
		
	}
	
}
