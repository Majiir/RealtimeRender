package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public class SimpleMaterialColor extends MaterialColor {

	private final Color color;
	
	public SimpleMaterialColor(Material material, Color color) {
		super(material);
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
