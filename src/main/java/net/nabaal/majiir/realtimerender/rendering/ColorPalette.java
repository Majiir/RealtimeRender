package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public interface ColorPalette {

	public Color getMaterialColor(Material material, int x, int z, double rainfall, double temperature, Biome biome);

}
