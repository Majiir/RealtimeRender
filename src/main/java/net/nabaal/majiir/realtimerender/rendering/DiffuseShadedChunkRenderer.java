package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;
import net.nabaal.majiir.realtimerender.image.ImageProvider;
import net.nabaal.majiir.realtimerender.palette.ColorPalette;
import net.nabaal.majiir.realtimerender.palette.MaterialColor;

import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.jscience.mathematics.vector.Float64Vector;

public class DiffuseShadedChunkRenderer implements ChunkRenderer {
	
	private final NormalMap normalMap;
	private final NormalMap structureMap;
	private final ImageProvider imageProvider;
	private final ColorPalette colorPalette;
	
	public DiffuseShadedChunkRenderer(ImageProvider imageProvider, NormalMap normalMap, NormalMap structureMap, ColorPalette colorPalette) {				
		this.normalMap = normalMap;
		this.structureMap = structureMap;
		this.imageProvider = imageProvider;
		this.colorPalette = colorPalette;
	}

	@Override
	public void render(ChunkSnapshot chunkSnapshot) {
		BufferedImage image = ImageProvider.createImage(Coordinate.SIZE_CHUNK);
		Graphics2D g = (Graphics2D) image.getGraphics();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				boolean seenStructure = false;
				// TODO: Handle cases where we can start at an opaque non-terrain block
				for (int y = TerrainHelper.getTerrainHeight(x, z, chunkSnapshot); y <= chunkSnapshot.getHighestBlockYAt(x, z); y++) {
					Material material = Material.getMaterial(chunkSnapshot.getBlockTypeId(x, y, z));
					Color color = getMaterialColor(material, chunkSnapshot, x, y, z);
					
					if (TerrainHelper.isTerrain(material)) {
						double shading = computeDiffuseShading(chunkSnapshot, x, z, this.normalMap);
						if (shading >= 0) {
							color = tintOrShadeColor(color, shading, 1.0);
						}
					} else if (TerrainHelper.isStructure(material) && !seenStructure) {
						seenStructure = true;
						double shading = computeDiffuseShading(chunkSnapshot, x, z, this.structureMap);
						if (shading >= 0) {
							if (material.equals(Material.LEAVES)) {
								color = tintOrShadeColor(color, shading, 0.6);
							} else {
								color = tintOrShadeColor(color, shading, 1.0);
							}
						}
					}
					
					g.setColor(color);
					g.fillRect(x, z, 1, 1);
				}
			}
		}
		imageProvider.setImage(Coordinate.fromSnapshot(chunkSnapshot), image);
	}
	
	private Color getMaterialColor(Material material, ChunkSnapshot chunkSnapshot, int x, int y, int z) {
		MaterialColor materialColor = colorPalette.getMaterialColor(material);
		if (materialColor != null) {
			Color color = materialColor.getColor(chunkSnapshot.getBlockData(x, y, z), x + (chunkSnapshot.getX() * 16), z + (chunkSnapshot.getZ() * 16), chunkSnapshot.getRawBiomeRainfall(x, z), chunkSnapshot.getRawBiomeTemperature(x, z), chunkSnapshot.getBiome(x, z));
			if (color != null) {
				if (material.equals(Material.STATIONARY_WATER) && isUnderWater(chunkSnapshot, x, y, z)) {
					color = setAlpha(color, 32);
				}
				return color;
			}
		}
		//RealtimeRender.getLogger().warning(String.format("RealtimeRender: missing color for material '%s'!", material.toString()));
		return new Color(0xFF00FF);
	}
	
	private static boolean isUnderWater(ChunkSnapshot chunkSnapshot, int x, int y, int z) {
		return Material.getMaterial(chunkSnapshot.getBlockTypeId(x, y + 1, z)) == Material.STATIONARY_WATER;
	}
	
	private static Color setAlpha(Color color, int a) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
	}
	
	private static Color tintOrShadeColor(Color color, double shading, double intensity) {
		shading = Math.min(Math.max(shading, 0), 1);
		if (shading > 0.5) {
			return tintColor(color, ((shading * 2) - 1) * intensity);
		} else {
			return shadeColor(color, (1 - (shading * 2)) * intensity);
		}
	}
	
	private static Color shadeColor(Color color, double shade) {
		return new Color(
				(int)(color.getRed() * (1.0 - shade)),
				(int)(color.getGreen() * (1.0 - shade)),
				(int)(color.getBlue() * (1.0 - shade)),
				color.getAlpha()
			);
	}
	
	private static Color tintColor(Color color, double shade) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		r += (int)((255 - r) * shade);
		g += (int)((255 - g) * shade);
		b += (int)((255 - b) * shade);
		return new Color(r, g, b, color.getAlpha());
	}
	
	private static double computeDiffuseShading(ChunkSnapshot chunkSnapshot, int x, int z, NormalMap nm) {	
		Float64Vector n = nm.getNormal(Coordinate.fromSnapshot(chunkSnapshot).zoomIn(Coordinate.OFFSET_BLOCK_CHUNK).plus(new Coordinate(x, z, Coordinate.LEVEL_BLOCK)));
		Float64Vector light = Float64Vector.valueOf(-1, -1, -1);
		if (n == null) {
			return -1;
		}
		double shading = n.times(light).divide((n.norm().times(light.norm()))).doubleValue();
		return ((shading + 1) * 0.4) + 0.15;
	}

}
