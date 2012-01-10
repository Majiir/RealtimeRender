package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.RealtimeRender;

import org.bukkit.Material;

public class DefaultColorPalette implements ColorPalette {

	private final ConcurrentMap<Material, MaterialColor> colors = new ConcurrentHashMap<Material, MaterialColor>();
	
	public DefaultColorPalette() {
		colors.put(Material.AIR, new SimpleMaterialColor(new Color(0, 0, 0, 0)));
		colors.put(Material.STONE, new SimpleMaterialColor(new Color(128, 132, 136)));
		colors.put(Material.VINE, new SimpleMaterialColor(new Color(0, 0xDD, 0, 32)));
		colors.put(Material.WATER_LILY, new SimpleMaterialColor(new Color(0, 0xDD, 0, 32)));
		colors.put(Material.YELLOW_FLOWER, new SimpleMaterialColor(new Color(0xDD, 0xDD, 0, 192)));
		colors.put(Material.DIRT, new SimpleMaterialColor(new Color(134, 96, 67)));
		colors.put(Material.COBBLESTONE, new SimpleMaterialColor(new Color(100, 100, 100)));
		colors.put(Material.WOOD, new SimpleMaterialColor(new Color(157, 128, 79)));
		colors.put(Material.SAPLING, new SimpleMaterialColor(new Color(120, 205, 120, 64)));
		colors.put(Material.BEDROCK, new SimpleMaterialColor(new Color(84, 84, 84)));
		colors.put(Material.STATIONARY_LAVA, new SimpleMaterialColor(new Color(255, 108, 16))); 
		colors.put(Material.LAVA, new SimpleMaterialColor(new Color(255, 108, 16)));
		colors.put(Material.SAND, new VariedMaterialColor(new SimpleMaterialColor(new Color(218, 210, 158)), 4, 8));
		colors.put(Material.GRAVEL, new SimpleMaterialColor(new Color(136, 126, 126)));
		colors.put(Material.GOLD_ORE, new SimpleMaterialColor(new Color(143, 140, 125)));
		colors.put(Material.IRON_ORE, new SimpleMaterialColor(new Color(136, 130, 127)));
		colors.put(Material.COAL_ORE, new SimpleMaterialColor(new Color(115, 115, 115)));
		colors.put(Material.LOG, new SimpleMaterialColor(new Color(102, 81, 51)));
		colors.put(Material.LAPIS_ORE, new SimpleMaterialColor(new Color(102, 112, 134, 255)));
		colors.put(Material.LAPIS_BLOCK, new SimpleMaterialColor(new Color(29, 71, 165, 255)));
		colors.put(Material.DISPENSER, new SimpleMaterialColor(new Color(107, 107, 107, 255)));
		colors.put(Material.SANDSTONE, new SimpleMaterialColor(new Color(218, 210, 158, 255)));
		colors.put(Material.NOTE_BLOCK, new SimpleMaterialColor(new Color(100, 67, 50, 255)));
		colors.put(Material.GOLD_BLOCK, new SimpleMaterialColor(new Color(255, 237, 140, 255)));
		colors.put(Material.IRON_BLOCK, new SimpleMaterialColor(new Color(217, 217, 217, 255)));
		colors.put(Material.DOUBLE_STEP, new SimpleMaterialColor(new Color(200, 200, 200, 255)));
		colors.put(Material.STEP, new SimpleMaterialColor(new Color(200, 200, 200, 255)));
		colors.put(Material.BRICK, new SimpleMaterialColor(new Color(86, 35, 23, 255))); 
		colors.put(Material.TNT, new SimpleMaterialColor(new Color(255, 0, 0, 255)));
		colors.put(Material.BOOKSHELF, new SimpleMaterialColor(new Color(191, 169, 116, 255)));
		colors.put(Material.MOSSY_COBBLESTONE, new SimpleMaterialColor(new Color(127, 174, 125, 255)));
		colors.put(Material.OBSIDIAN, new SimpleMaterialColor(new Color(17, 13, 26, 255))); 
		colors.put(Material.TORCH, new SimpleMaterialColor(new Color(255, 225, 96, 208))); 
		colors.put(Material.FIRE, new SimpleMaterialColor(new Color(224, 174, 21, 255))); 
		colors.put(Material.WOOD_STAIRS, new SimpleMaterialColor(new Color(191, 169, 116, 255)));
		colors.put(Material.CHEST, new SimpleMaterialColor(new Color(191, 135, 2, 255)));
		colors.put(Material.REDSTONE_WIRE, new SimpleMaterialColor(new Color(111, 1, 1, 255))); 
		colors.put(Material.DIAMOND_ORE, new SimpleMaterialColor(new Color(129, 140, 143, 255)));
		colors.put(Material.DIAMOND_BLOCK, new SimpleMaterialColor(new Color(45, 166, 152, 255))); 
		colors.put(Material.WORKBENCH, new SimpleMaterialColor(new Color(169, 107, 0, 255))); 
		colors.put(Material.CROPS, new SimpleMaterialColor(new Color(144, 188, 39, 128))); 
		colors.put(Material.SOIL, new SimpleMaterialColor(new Color(134, 96, 67, 255))); 
		colors.put(Material.FURNACE, new SimpleMaterialColor(new Color(188, 188, 188, 255)));
		colors.put(Material.BURNING_FURNACE, new SimpleMaterialColor(new Color(221, 221, 221, 255))); 
		colors.put(Material.RAILS, new SimpleMaterialColor(new Color(120, 120, 120, 128)));
		colors.put(Material.COBBLESTONE_STAIRS, new SimpleMaterialColor(new Color(120, 120, 120, 128)));
		colors.put(Material.STONE_PLATE, new SimpleMaterialColor(new Color(120, 120, 120, 255)));
		colors.put(Material.REDSTONE_ORE, new SimpleMaterialColor(new Color(143, 125, 125, 255)));
		colors.put(Material.GLOWING_REDSTONE_ORE, new SimpleMaterialColor(new Color(163, 145, 145, 255)));
		colors.put(Material.REDSTONE_TORCH_OFF, new SimpleMaterialColor(new Color(181, 140, 64, 32))); 
		colors.put(Material.REDSTONE_TORCH_ON, new SimpleMaterialColor(new Color(255, 0, 0, 176))); 
		colors.put(Material.STONE_BUTTON, new SimpleMaterialColor(new Color(128, 128, 128, 16))); 
		colors.put(Material.SNOW, new SimpleMaterialColor(new Color(245, 245, 245, 255)));
		colors.put(Material.ICE, new SimpleMaterialColor(new Color(150, 192, 255, 150)));
		colors.put(Material.SNOW_BLOCK, new SimpleMaterialColor(new Color(205, 205, 205, 255)));
		colors.put(Material.CACTUS, new SimpleMaterialColor(new Color(85, 107, 47, 255))); 
		colors.put(Material.CLAY, new SimpleMaterialColor(new Color(144, 152, 168, 255)));
		colors.put(Material.SUGAR_CANE_BLOCK, new SimpleMaterialColor(new Color(193, 234, 150, 255)));
		colors.put(Material.JUKEBOX, new SimpleMaterialColor(new Color(125, 66, 44, 255))); 
		colors.put(Material.FENCE, new SimpleMaterialColor(new Color(88, 54, 22, 200))); 
		colors.put(Material.PUMPKIN, new SimpleMaterialColor(new Color(227, 144, 29, 255))); 
		colors.put(Material.NETHERRACK, new SimpleMaterialColor(new Color(194, 115, 115, 255)));
		colors.put(Material.SOUL_SAND, new SimpleMaterialColor(new Color(121, 97, 82, 255))); 
		colors.put(Material.GLOWSTONE, new SimpleMaterialColor(new Color(255, 188, 94, 255))); 
		colors.put(Material.PORTAL, new SimpleMaterialColor(new Color(60, 13, 106, 127))); 
		colors.put(Material.JACK_O_LANTERN, new SimpleMaterialColor(new Color(227, 144, 29, 255))); 
		colors.put(Material.CAKE_BLOCK, new SimpleMaterialColor(new Color(228, 205, 206, 255)));
		colors.put(Material.RED_ROSE, new SimpleMaterialColor(new Color(111, 1, 1, 255)));
		colors.put(Material.SMOOTH_BRICK, new SimpleMaterialColor(new Color(128, 132, 136)));
		colors.put(Material.SMOOTH_STAIRS, new SimpleMaterialColor(new Color(128, 132, 136)));
		colors.put(Material.MELON_BLOCK, new SimpleMaterialColor(new Color(153, 194, 110, 255)));
		colors.put(Material.MELON_STEM, new SimpleMaterialColor(new Color(102, 81, 51, 64)));
		colors.put(Material.PUMPKIN_STEM, new SimpleMaterialColor(new Color(102, 81, 51, 64)));
		colors.put(Material.MYCEL, new SimpleMaterialColor(new Color(0x7b6e83)));
		colors.put(Material.HUGE_MUSHROOM_1, new SimpleMaterialColor(new Color(102, 81, 51)));
		colors.put(Material.HUGE_MUSHROOM_2, new SimpleMaterialColor(new Color(111, 1, 1, 255)));
		colors.put(Material.DEAD_BUSH, new SimpleMaterialColor(new Color(157, 128, 79, 70)));
		Map<Integer, MaterialColor> woolColors = new HashMap<Integer, MaterialColor>();
		woolColors.put(0x0, new SimpleMaterialColor(new Color(0xdcdcdc))); // white
		woolColors.put(0x1, new SimpleMaterialColor(new Color(0xe77e34))); // orange
		woolColors.put(0x2, new SimpleMaterialColor(new Color(0xc050c8))); // magenta
		woolColors.put(0x3, new SimpleMaterialColor(new Color(0x6084c2))); // light blue
		woolColors.put(0x4, new SimpleMaterialColor(new Color(0xbdb520))); // yellow
		woolColors.put(0x5, new SimpleMaterialColor(new Color(0x43b428))); // lime
		woolColors.put(0x6, new SimpleMaterialColor(new Color(0xcf7a95))); // pink
		woolColors.put(0x7, new SimpleMaterialColor(new Color(0x424545))); // gray
		woolColors.put(0x8, new SimpleMaterialColor(new Color(0x9da3a3))); // light gray
		woolColors.put(0x9, new SimpleMaterialColor(new Color(0x2b729d))); // cyan
		woolColors.put(0xA, new SimpleMaterialColor(new Color(0x7335c2))); // purple
		woolColors.put(0xB, new SimpleMaterialColor(new Color(0x2a379b))); // blue
		woolColors.put(0xC, new SimpleMaterialColor(new Color(0x5f3a23))); // brown
		woolColors.put(0xD, new SimpleMaterialColor(new Color(0x3a5a29))); // green
		woolColors.put(0xE, new SimpleMaterialColor(new Color(0x9f2f28))); // red
		woolColors.put(0xF, new SimpleMaterialColor(new Color(0x241819))); // black	
		colors.put(Material.WOOL, new MetadataMaterialColor(woolColors));
		BufferedImage foliage = null;
		BufferedImage grass = null;
		BufferedImage longgrass = null;
		BufferedImage water = null;
		try {
			foliage = ImageIO.read(getClass().getResource("/images/foliagecolor.png"));
			grass = ImageIO.read(getClass().getResource("/images/grasscolor.png"));
			longgrass = ImageIO.read(getClass().getResource("/images/longgrasscolor.png"));
			water = ImageIO.read(getClass().getResource("/images/watercolor.png"));
		} catch (IOException e) {
			RealtimeRender.getLogger().severe("Failed to load palette resources!");
		}
		colors.put(Material.GRASS, new ClimateMaterialColor(grass));
		colors.put(Material.LONG_GRASS, new TransparentMaterialColor(new ClimateMaterialColor(longgrass), 0.375));
		colors.put(Material.STATIONARY_WATER, new TransparentMaterialColor(new ClimateMaterialColor(water), 0.75));
		colors.put(Material.WATER, colors.get(Material.STATIONARY_WATER));
		Map<Integer, MaterialColor> leaves = new HashMap<Integer, MaterialColor>();
		leaves.put(0x0, new TransparentMaterialColor(new ClimateMaterialColor(foliage), 0.375));
		leaves.put(0x1, leaves.get(0x0));
		leaves.put(0x2, new TransparentMaterialColor(new ClimateMaterialColor(foliage, true), 0.375));
		leaves.put(0x3, leaves.get(0x0));
		colors.put(Material.LEAVES, new MetadataMaterialColor(leaves));
	}
	
	@Override
	public MaterialColor getMaterialColor(Material material) {
		return colors.get(material);
	}
	
	public static Color setAlpha(Color color, int a) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
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
	
	public static Coordinate getBiomeColorIndex(double rainfall, double temperature) {
		rainfall *= temperature;
		int i = (int)((1.0d - temperature) * 255.0d);
		int j = (int)((1.0d - rainfall) * 255.0d);
		
		// TODO: Refactor without using Coordinate
		return new Coordinate(i, j, Coordinate.LEVEL_BLOCK);
	}

}
