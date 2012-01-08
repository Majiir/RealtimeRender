package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.block.Biome;

public interface MaterialColor {
	
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome);

}
