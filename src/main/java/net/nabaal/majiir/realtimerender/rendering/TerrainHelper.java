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
		Material.SNOW,
		Material.MYCEL,
		Material.NETHERRACK,
	}));
	
	public static boolean isTerrain(Material material) {
		return terrain.contains(material);
	}
	
	public static byte getTerrainHeight(int x, int z, ChunkSnapshot snapshot) {
		for (int y = Math.min(snapshot.getHighestBlockYAt(x, z) + 1, 127); i >= 0; i--) {
			if (isTerrain(Material.getMaterial(snapshot.getBlockTypeId(x, y, z)))) {
				return (byte) y;
			}
		}
		return HeightMap.NO_HEIGHT_INFORMATION;
	}
	
}
