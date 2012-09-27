package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.block.Biome;

class BiomeMaterialColor implements MaterialColor {
	
	private final ConcurrentMap<Biome, MaterialColor> colors = new ConcurrentHashMap<Biome, MaterialColor>();
	private final MaterialColor fallback;
	
	public BiomeMaterialColor(Map<Biome, MaterialColor> colors) {
		this(colors, null);
	}
	
	public BiomeMaterialColor(Map<Biome, MaterialColor> colors, MaterialColor fallback) {
		this.colors.putAll(colors);
		this.fallback = fallback;
	}
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		MaterialColor color = colors.get(biome);
		if (color == null) {
			if (fallback == null) {
				return null;
			}
			color = fallback;
		}
		return color.getColor(data, x, z, rainfall, temperature, biome);
	}

}
