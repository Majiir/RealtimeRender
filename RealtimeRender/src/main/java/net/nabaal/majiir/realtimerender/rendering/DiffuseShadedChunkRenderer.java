package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;
import net.nabaal.majiir.realtimerender.image.ImageProvider;

import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.jscience.mathematics.vector.Float64Vector;

public class DiffuseShadedChunkRenderer implements ChunkRenderer {
	
	private static BufferedImage grassColor = null;
	private static BufferedImage waterColor = null;
	private static BufferedImage foliageColor = null;
	private final NormalMap normalMap;
	private final ImageProvider imageProvider;
	
	public DiffuseShadedChunkRenderer(ImageProvider imageProvider, NormalMap normalMap) {		
		try {
			grassColor = ImageIO.read(getClass().getResource("/images/grasscolor.png"));
			waterColor = ImageIO.read(getClass().getResource("/images/watercolor.png"));
			foliageColor = ImageIO.read(getClass().getResource("/images/foliagecolor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.normalMap = normalMap;
		this.imageProvider = imageProvider;
	}

	@Override
	public void render(ChunkSnapshot chunkSnapshot) {
		BufferedImage image = ImageProvider.createImage(Coordinate.SIZE_CHUNK);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int ymax = chunkSnapshot.getHighestBlockYAt(x, z);
				// TODO: Handle cases where we can start at an opaque non-terrain block
				for (int y = getTerrainHeight(chunkSnapshot, x, z); y <= ymax; y++) {
					Color color;
					Material material = Material.getMaterial(chunkSnapshot.getBlockTypeId(x, y, z));
					color = getMaterialColor(material, chunkSnapshot, x, y, z);
					g.setColor(color);
					g.fillRect(x, z, 1, 1);
					
					if (TerrainHelper.isTerrain(material)) {
						double shading = computeDiffuseShading(chunkSnapshot, x, z);
						if (shading >= 0) {
							g.setColor(computeShadeColor(shading));
							g.fillRect(x, z, 1, 1);
						}
					}
				}
			}
		}
		imageProvider.setImage(Coordinate.fromSnapshot(chunkSnapshot), image);
	}
	
	public static Color getMaterialColor(Material material, ChunkSnapshot chunkSnapshot, int x, int y, int z) {
		
		Color color = getSimpleMaterialColor(material);
		
		if (color == null) {
		
			switch (material) {
			case LEAVES:
				color = computeShadedColor(setAlpha(new Color(getBiomeFoliageColor(chunkSnapshot, x, y, z)), 96), 0.55);
				break;
			case GRASS:
				color = computeShadedColor(new Color(getBiomeGrassColor(chunkSnapshot, x, z)), 0.9);
				break;
			case STATIONARY_WATER:
				color = computeShadedColor(setAlpha(new Color(getBiomeWaterColor(chunkSnapshot, x, z)), 192), 0.7);
				if (isUnderWater(chunkSnapshot, x, y, z)) {
					color = setAlpha(color, 32);
				}
				break;
			case LONG_GRASS:
				color = setAlpha(computeShadedColor(new Color(getBiomeGrassColor(chunkSnapshot, x, z)), 0.5), 96);
				break;
			default:
				//RealtimeRender.getLogger().warning(String.format("RealtimeRender: missing color for material '%s'!", material.toString()));
				color = new Color(0xFF00FF);
			}
		
		}
		
		return color;
	}
	
	private static Color getSimpleMaterialColor(Material material) {
		switch (material) {
			case AIR:			return new Color(0, 0, 0, 0);
			case TORCH:			return new Color(0xFF, 0xFF, 0x40, 223);
			case DIRT:
			case LOG:
			case SOIL:			return new Color(0x964B00);
			case BROWN_MUSHROOM:
			case WOOD:			return new Color(0xc69430);
			case SAND:			return new Color(0xDDDDAA);
			case SANDSTONE:		return new Color(0xDDDDAA);
			case STONE:			return new Color(0x777788);
			case BEDROCK:		return new Color(0x555566);
			case CLAY:			return new Color(0xaaaabb);
			case GRAVEL:
			case BRICK:			return new Color(0x7f7777);
			case CROPS:			return new Color(0, 0xBB, 0, 48);
			case VINE:			return new Color(0, 0xDD, 0, 32);
			case RED_ROSE:
			case REDSTONE_WIRE:	return new Color(0xAA, 0, 0, 192);
			case YELLOW_FLOWER:	return new Color(0xDD, 0xDD, 0, 192);
			
			default: return null;
		}
	}

	private static boolean isUnderWater(ChunkSnapshot chunkSnapshot, int x, int y, int z) {
		return Material.getMaterial(chunkSnapshot.getBlockTypeId(x, y + 1, z)) == Material.STATIONARY_WATER;
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
	
	
	public double computeDiffuseShading(ChunkSnapshot chunkSnapshot, int x, int z) {	
		Float64Vector n = computeTerrainNormal(chunkSnapshot, x, z);
		Float64Vector light = Float64Vector.valueOf(-1, -1, -1);
		if (n == null) {
			return -1;
		}
		double shading = n.times(light).divide((n.norm().times(light.norm()))).doubleValue();
		return ((shading + 1) * 0.4) + 0.15;
	}
	
	
	public Float64Vector computeTerrainNormal(ChunkSnapshot chunkSnapshot, int x, int z) {
		Coordinate chunk = Coordinate.fromSnapshot(chunkSnapshot);
		return this.normalMap.getNormal(chunk.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, z, Coordinate.LEVEL_BLOCK))); 
	}
	

	public static int getTerrainHeight(ChunkSnapshot chunkSnapshot, int x, int z) {
		int y = chunkSnapshot.getHighestBlockYAt(x, z);
		Material material;
		do {
			y--;
			int id = chunkSnapshot.getBlockTypeId(x, y, z);
			material = Material.getMaterial(id);
		} while (!TerrainHelper.isTerrain(material) && (y > 0));
		return y;
	}
	
	public static int getBiomeFoliageColor(ChunkSnapshot chunkSnapshot, int x, int y, int z) {
		Coordinate coordinate = getBiomeColorIndex(chunkSnapshot, x, z);
		// TODO: Test all four leaves types.
		if ((chunkSnapshot.getBlockData(x, y, z) & 0x3) == 0x2) {
			return foliageColor.getRGB(255 - coordinate.getX(), 255 - coordinate.getY());
		} else {
			return foliageColor.getRGB(coordinate.getX(), coordinate.getY());
		}
	}
	
	public static int getBiomeGrassColor(ChunkSnapshot chunkSnapshot, int x, int z) {
		Coordinate coordinate = getBiomeColorIndex(chunkSnapshot, x, z);
		return grassColor.getRGB(coordinate.getX(), coordinate.getY());
	}
	
	public static int getBiomeWaterColor(ChunkSnapshot chunkSnapshot, int x, int z) {
		Coordinate coordinate = getBiomeColorIndex(chunkSnapshot, x, z);
		return waterColor.getRGB(coordinate.getX(), coordinate.getY());
	}
	
	public static Coordinate getBiomeColorIndex(ChunkSnapshot chunkSnapshot, int x, int z) {
		double rainfall = chunkSnapshot.getRawBiomeRainfall(x, z);
		double temperature = chunkSnapshot.getRawBiomeTemperature(x, z);
		
		rainfall *= temperature;
		int i = (int)((1.0d - temperature) * 255.0d);
		int j = (int)((1.0d - rainfall) * 255.0d);
		
		// TODO: Refactor without using Coordinate
		return new Coordinate(i, j, Coordinate.LEVEL_BLOCK);
	}

}
