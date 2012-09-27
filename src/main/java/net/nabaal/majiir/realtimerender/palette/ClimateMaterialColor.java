package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.bukkit.block.Biome;

class ClimateMaterialColor implements MaterialColor {
	
	private final BufferedImage gradient;
	private final boolean flip;
	
	public ClimateMaterialColor(BufferedImage gradient) {
		this(gradient, false);
	}
	
	public ClimateMaterialColor(BufferedImage gradient, boolean flip) {
		this.gradient = gradient;
		this.flip = flip;
	}

	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		rainfall *= temperature;
		int width = gradient.getWidth() - 1;
		int height = gradient.getHeight() - 1;
		int i = (int)((1.0d - temperature) * width);
		int j = (int)((1.0d - rainfall) * height);
		if (flip) {
			i = width - i;
			j = height - j;
		}
		return new Color(gradient.getRGB(i, j), true);
	}
	
}