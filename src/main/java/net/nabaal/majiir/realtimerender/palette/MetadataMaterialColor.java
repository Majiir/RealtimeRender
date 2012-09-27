package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.block.Biome;

class MetadataMaterialColor implements MaterialColor {
	
	private final ConcurrentMap<Integer, MaterialColor> colors = new ConcurrentHashMap<Integer, MaterialColor>();
	private final MaterialColor fallback;
	private final int mask;
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors) {
		this(colors, null);
	}
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors, MaterialColor fallback) {
		this(colors, fallback, 0xFF);
	}
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors, int mask) {
		this(colors, null, mask);
	}
	
	public MetadataMaterialColor(Map<Integer, MaterialColor> colors, MaterialColor fallback, int mask) {
		this.colors.putAll(colors);
		this.fallback = fallback;
		this.mask = mask;
	}
	
	@Override
	public Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		MaterialColor color = colors.get(data & mask);
		if (color == null) {
			if (fallback == null) {
				return null;
			}
			color = fallback;
		}
		return color.getColor(data, x, z, rainfall, temperature, biome);
	}

}
