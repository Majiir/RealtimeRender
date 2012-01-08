package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.block.Biome;

public abstract class MetadataMaterialColor implements MaterialColor {

	public abstract Color getColor(int data);
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		return getColor(data);
	}

}
