package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;

import org.bukkit.block.Biome;

public class TransparentMaterialColor implements MaterialColor {

	private final MaterialColor source;
	private final double multiplier;
	
	public TransparentMaterialColor(MaterialColor source, double multiplier) {
		this.source = source;
		this.multiplier = multiplier;
	}
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		Color color = source.getColor(data, x, z, rainfall, temperature, biome);
		if (color == null) { return null; }
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * multiplier));
	}

}
