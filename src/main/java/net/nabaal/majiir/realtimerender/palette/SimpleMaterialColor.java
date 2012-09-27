package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;

import org.bukkit.block.Biome;

class SimpleMaterialColor implements MaterialColor {

	private final Color color;
	
	public SimpleMaterialColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	@Override
	public final Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		return getColor();
	}

}
