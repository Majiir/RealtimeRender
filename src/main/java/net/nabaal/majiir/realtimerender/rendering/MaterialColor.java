package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public abstract class MaterialColor {

	private final Material material;
	
	public MaterialColor(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public abstract Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome);

}
