package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.util.Random;

import org.bukkit.block.Biome;

public class SparklingMaterialColor implements MaterialColor {

	private final MaterialColor source;
	private final int min;
	private final int max;
	private final double chance;
	
	public SparklingMaterialColor(MaterialColor source, int min, int max, double chance) {
		this.source = source;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}

	public Color getColor(int x, int z, Color color) {
		if (color == null) { return null; }
		final int prime = 29;
		int hash = 1;
		hash = prime * hash + x;
		hash = prime * hash + z;
		Random random = new Random(hash);
		if (random.nextDouble() < chance) {
			int mod = random.nextInt(max - min) + min;
			int r = color.getRed() + mod;
			int g = color.getGreen() + mod;
			int b = color.getBlue() + mod;
			return new Color(r, g, b, color.getAlpha());
		} else {
			return color;
		}
	}
	
	@Override
	public final Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		return getColor(x, z, source.getColor(data, x, z, rainfall, temperature, biome));
	}

}
