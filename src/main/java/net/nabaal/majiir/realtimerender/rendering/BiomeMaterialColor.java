package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public abstract class BiomeMaterialColor extends MaterialColor {

	public BiomeMaterialColor(Material material) {
		super(material);
	}
	
	public abstract Color getColor(double rainfall, double temperature, Biome biome);

	@Override
	public final Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		return getColor(rainfall, temperature, biome);
	}

}
