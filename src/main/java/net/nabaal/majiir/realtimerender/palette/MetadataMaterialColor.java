package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.block.Biome;

public class MetadataMaterialColor implements MaterialColor {
	
	private final ConcurrentMap<Integer, MaterialColor> colors = new ConcurrentHashMap<Integer, MaterialColor>();
	private final MaterialColor fallback;
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors) {
		this(colors, null);
	}
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors, MaterialColor fallback) {
		this.colors.putAll(colors);
		this.fallback = fallback;
	}
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		MaterialColor color = colors.get(data);
		if (color == null) {
			if (fallback == null) {
				return null;
			}
			color = fallback;
		}
		return color.getColor(data, x, z, rainfall, temperature, biome);
	}

}
