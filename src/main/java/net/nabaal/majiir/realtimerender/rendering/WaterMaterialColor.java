package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.bukkit.block.Biome;

public class WaterMaterialColor extends ClimateMaterialColor {

	private static BufferedImage waterColor = null;
	
	public WaterMaterialColor() {
		try {
			waterColor = ImageIO.read(getClass().getResource("/images/watercolor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Color getColor(double rainfall, double temperature, Biome biome) {
		return DefaultColorPalette.computeShadedColor(DefaultColorPalette.setAlpha(new Color(getBiomeWaterColor(rainfall, temperature)), 192), 0.7);
	}
	
	public static int getBiomeWaterColor(double rainfall, double temperature) {
		Coordinate coordinate = DefaultColorPalette.getBiomeColorIndex(rainfall, temperature);
		return waterColor.getRGB(coordinate.getX(), coordinate.getY());
	}

}
