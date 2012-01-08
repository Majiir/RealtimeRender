package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public class DefaultColorPalette implements ColorPalette {

	private static BufferedImage grassColor = null;
	private static BufferedImage waterColor = null;
	private static BufferedImage foliageColor = null;
	
	public DefaultColorPalette() {
		try {
			grassColor = ImageIO.read(getClass().getResource("/images/grasscolor.png"));
			waterColor = ImageIO.read(getClass().getResource("/images/watercolor.png"));
			foliageColor = ImageIO.read(getClass().getResource("/images/foliagecolor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Color getMaterialColor(Material material, int data, int x, int z, double rainfall, double temperature, Biome biome) {
		x %= 16;
		z %= 16;
		if (x < 0) { x += 16; }
		if (z < 0) { z += 16; }
		
		switch (material) {
			case AIR: return new Color(0, 0, 0, 0);
			case STONE: return new Color(128, 132, 136);
			case VINE: return new Color(0, 0xDD, 0, 32);
			case WATER_LILY: return new Color(0, 0xDD, 0, 32);
			case YELLOW_FLOWER: return new Color(0xDD, 0xDD, 0, 192);
			case DIRT: return new Color(134, 96, 67);
			case COBBLESTONE: return new Color(100, 100, 100);
			case WOOD: return new Color(157, 128, 79);
			case SAPLING: return new Color(120, 205, 120, 64);
			case BEDROCK: return new Color(84, 84, 84);
			case STATIONARY_LAVA: 
			case LAVA: return new Color(255, 108, 16);
			case SAND: return new Color(218, 210, 158);
			case GRAVEL: return new Color(136, 126, 126);
			case GOLD_ORE: return new Color(143, 140, 125);
			case IRON_ORE: return new Color(136, 130, 127);
			case COAL_ORE: return new Color(115, 115, 115);
			case LOG: return new Color(102, 81, 51);
			case LAPIS_ORE: return new Color(102, 112, 134, 255);
			case LAPIS_BLOCK: return new Color(29, 71, 165, 255);
			case DISPENSER: return new Color(107, 107, 107, 255);
			case SANDSTONE: return new Color(218, 210, 158, 255);
			case NOTE_BLOCK: return new Color(100, 67, 50, 255);
			case GOLD_BLOCK: return new Color(255, 237, 140, 255);
			case IRON_BLOCK: return new Color(217, 217, 217, 255);
			case DOUBLE_STEP: return new Color(200, 200, 200, 255);
			case STEP: return new Color(200, 200, 200, 255);
			case BRICK: return new Color(86, 35, 23, 255); 
			case TNT: return new Color(255, 0, 0, 255);
			case BOOKSHELF: return new Color(191, 169, 116, 255);
			case MOSSY_COBBLESTONE: return new Color(127, 174, 125, 255);
			case OBSIDIAN: return new Color(17, 13, 26, 255); 
			case TORCH: return new Color(255, 225, 96, 208); 
			case FIRE: return new Color(224, 174, 21, 255); 
			case WOOD_STAIRS: return new Color(191, 169, 116, 255);
			case CHEST: return new Color(191, 135, 2, 255);
			case REDSTONE_WIRE: return new Color(111, 1, 1, 255); 
			case DIAMOND_ORE: return new Color(129, 140, 143, 255);
			case DIAMOND_BLOCK: return new Color(45, 166, 152, 255); 
			case WORKBENCH: return new Color(169, 107, 0, 255); 
			case CROPS: return new Color(144, 188, 39, 128); 
			case SOIL: return new Color(134, 96, 67, 255); 
			case FURNACE: return new Color(188, 188, 188, 255);
			case BURNING_FURNACE: return new Color(221, 221, 221, 255); 
			case RAILS: return new Color(120, 120, 120, 128);
			case COBBLESTONE_STAIRS: return new Color(120, 120, 120, 128);
			case STONE_PLATE: return new Color(120, 120, 120, 255);
			case REDSTONE_ORE: return new Color(143, 125, 125, 255);
			case GLOWING_REDSTONE_ORE: return new Color(163, 145, 145, 255);
			case REDSTONE_TORCH_OFF: return new Color(181, 140, 64, 32); 
			case REDSTONE_TORCH_ON: return new Color(255, 0, 0, 176); 
			case STONE_BUTTON: return new Color(128, 128, 128, 16); 
			case SNOW: return new Color(245, 245, 245, 255);
			case ICE: return new Color(150, 192, 255, 150);
			case SNOW_BLOCK: return new Color(205, 205, 205, 255);
			case CACTUS: return new Color(85, 107, 47, 255); 
			case CLAY: return new Color(144, 152, 168, 255);
			case SUGAR_CANE_BLOCK: return new Color(193, 234, 150, 255);
			case JUKEBOX: return new Color(125, 66, 44, 255); 
			case FENCE: return new Color(88, 54, 22, 200); 
			case PUMPKIN: return new Color(227, 144, 29, 255); 
			case NETHERRACK: return new Color(194, 115, 115, 255);
			case SOUL_SAND: return new Color(121, 97, 82, 255); 
			case GLOWSTONE: return new Color(255, 188, 94, 255); 
			case PORTAL: return new Color(60, 13, 106, 127); 
			case JACK_O_LANTERN: return new Color(227, 144, 29, 255); 
			case CAKE_BLOCK: return new Color(228, 205, 206, 255);
			case RED_ROSE: return new Color(111, 1, 1, 255);
			case SMOOTH_BRICK: return new Color(128, 132, 136);
			case SMOOTH_STAIRS: return new Color(128, 132, 136);
			case MELON_BLOCK: return new Color(153, 194, 110, 255);
			case MELON_STEM: return new Color(102, 81, 51, 64);
			case PUMPKIN_STEM: return new Color(102, 81, 51, 64);
			case MYCEL: return new Color(0x7b6e83);
			case HUGE_MUSHROOM_1: return new Color(111, 1, 1, 255);
			case HUGE_MUSHROOM_2: return new Color(102, 81, 51);
			case DEAD_BUSH: return new Color(157, 128, 79, 70);
			case LEAVES:
				return computeShadedColor(setAlpha(new Color(getBiomeFoliageColor(rainfall, temperature, x, z, data)), 96), 0.55);
			case GRASS:
				return computeShadedColor(new Color(getBiomeGrassColor(rainfall, temperature, x, z)), 0.9);
			case STATIONARY_WATER:
				Color color = computeShadedColor(setAlpha(new Color(getBiomeWaterColor(rainfall, temperature, x, z)), 192), 0.7);
				return color;
			case LONG_GRASS:
				return setAlpha(computeShadedColor(new Color(getBiomeGrassColor(rainfall, temperature, x, z)), 0.5), 96);
			case WOOL:
				int colorVal = data & 0xF;
				switch (colorVal) {
					case 0: return new Color(0xdcdcdc); // white
					case 1: return new Color(0xe77e34); // orange
					case 2: return new Color(0xc050c8); // magenta
					case 3: return new Color(0x6084c2); // light blue
					case 4: return new Color(0xbdb520); // yellow
					case 5: return new Color(0x43b428); // lime
					case 6: return new Color(0xcf7a95); // pink
					case 7: return new Color(0x424545); // gray
					case 8: return new Color(0x9da3a3); // light gray
					case 9: return new Color(0x2b729d); // cyan
					case 10: return new Color(0x7335c2); // purple
					case 11: return new Color(0x2a379b); // blue
					case 12: return new Color(0x5f3a23); // brown
					case 13: return new Color(0x3a5a29); // green
					case 14: return new Color(0x9f2f28); // red
					case 15: return new Color(0x241819); // black
				}
			default: return null;
		}
	}
	
	public static Color setAlpha(Color color, int a) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
	}
	
	public static Color computeShadeColor(double shading) {
		if (shading < 0.5) {
			return new Color(0, 0, 0, (int) Math.floor((1 - (shading * 2)) * 255));
		} else if (shading < 1.0) {
			return new Color(255, 255, 255, (int) Math.floor(((shading * 2) - 1) * 255));
		} else {
			return new Color(255, 255, 255, 255);
		}
	}
	
	public static Color computeShadedColor(Color color, double multiplier) {		
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		if (multiplier < 1) {
			r *= multiplier;
			g *= multiplier;
			b *= multiplier;	
		}
		
		r *= multiplier;
		g *= multiplier;
		b *= multiplier;
	
		r = Math.min(r, 255);
		g = Math.min(g, 255);
		b = Math.min(b, 255);
		
		r = Math.max(r, 0);
		g = Math.max(g, 0);
		b = Math.max(b, 0);
		
		return new Color(r, g, b, color.getAlpha());
	}
	
	public static int getBiomeFoliageColor(double rainfall, double temperature, int x, int z, int data) {
		Coordinate coordinate = getBiomeColorIndex(rainfall, temperature, x, z);
		if ((data & 0x3) == 0x2) {
			return foliageColor.getRGB(255 - coordinate.getX(), 255 - coordinate.getY());
		} else {
			return foliageColor.getRGB(coordinate.getX(), coordinate.getY());
		}
	}
	
	public static int getBiomeGrassColor(double rainfall, double temperature, int x, int z) {
		Coordinate coordinate = getBiomeColorIndex(rainfall, temperature, x, z);
		return grassColor.getRGB(coordinate.getX(), coordinate.getY());
	}
	
	public static int getBiomeWaterColor(double rainfall, double temperature, int x, int z) {
		Coordinate coordinate = getBiomeColorIndex(rainfall, temperature, x, z);
		return waterColor.getRGB(coordinate.getX(), coordinate.getY());
	}
	
	public static Coordinate getBiomeColorIndex(double rainfall, double temperature, int x, int z) {
		rainfall *= temperature;
		int i = (int)((1.0d - temperature) * 255.0d);
		int j = (int)((1.0d - rainfall) * 255.0d);
		
		// TODO: Refactor without using Coordinate
		return new Coordinate(i, j, Coordinate.LEVEL_BLOCK);
	}

}
