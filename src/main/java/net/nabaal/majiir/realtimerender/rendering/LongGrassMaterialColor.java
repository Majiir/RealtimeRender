package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.bukkit.block.Biome;

public class LongGrassMaterialColor extends ClimateMaterialColor {

	private static BufferedImage grassColor = null;
	
	public LongGrassMaterialColor() {
		try {
			grassColor = ImageIO.read(getClass().getResource("/images/grasscolor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Color getColor(double rainfall, double temperature, Biome biome) {
		return DefaultColorPalette.setAlpha(DefaultColorPalette.computeShadedColor(new Color(getBiomeGrassColor(rainfall, temperature)), 0.5), 96);
	}
	
	public static int getBiomeGrassColor(double rainfall, double temperature) {
		Coordinate coordinate = DefaultColorPalette.getBiomeColorIndex(rainfall, temperature);
		return grassColor.getRGB(coordinate.getX(), coordinate.getY());
	}

}
