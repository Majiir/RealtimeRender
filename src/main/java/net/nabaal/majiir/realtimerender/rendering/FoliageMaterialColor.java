package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.bukkit.block.Biome;

public class FoliageMaterialColor implements MaterialColor {

	private static BufferedImage foliageColor = null;
	
	public FoliageMaterialColor() {
		try {
			foliageColor = ImageIO.read(getClass().getResource("/images/foliagecolor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		return DefaultColorPalette.computeShadedColor(DefaultColorPalette.setAlpha(new Color(getBiomeFoliageColor(rainfall, temperature, data)), 96), 0.55);
	}
	
	public static int getBiomeFoliageColor(double rainfall, double temperature, int data) {
		Coordinate coordinate = DefaultColorPalette.getBiomeColorIndex(rainfall, temperature);
		if ((data & 0x3) == 0x2) {
			return foliageColor.getRGB(255 - coordinate.getX(), 255 - coordinate.getY());
		} else {
			return foliageColor.getRGB(coordinate.getX(), coordinate.getY());
		}
	}

}
