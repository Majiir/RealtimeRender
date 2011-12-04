package net.nabaal.majiir.realtimerender.rendering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;

public final class TerrainHelper {

	private TerrainHelper() throws InstantiationException {
		throw new InstantiationException("Cannot instantiate a helper class.");
	} 
	
	private static Set<Material> terrain = new HashSet<Material>(Arrays.asList(new Material[] {
		Material.SAND,
		Material.SANDSTONE,
		Material.BEDROCK,
		Material.CLAY, 
		Material.DIRT,
		Material.GRASS,
		Material.GRAVEL,
		Material.STONE,
	}));
	
	public static boolean isTerrain(Material material) {
		return terrain.contains(material);
	}
	
	public static int getTerrainHeight(int x, int z, ChunkSnapshot snapshot) {
		Material material;
		int y = snapshot.getHighestBlockYAt(x, z) + 1;
		do {
			y--;
			material = Material.getMaterial(snapshot.getBlockTypeId(x, y, z));
		} while (!isTerrain(material) && (y > -1));
		if (y == -1) {
			y = HeightMap.NO_HEIGHT_INFORMATION;
		}
		return y;
	}
	
}
