package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;

import org.bukkit.ChunkSnapshot;

public class HeightMapRenderer implements ChunkRenderer {

	private final ImageHeightMap heightMap;
	
	public HeightMapRenderer(ImageHeightMap heightMap) {
		this.heightMap = heightMap;
	}
	
	@Override
	public void render(ChunkSnapshot snapshot) {
		Coordinate chunk = Coordinate.fromSnapshot(snapshot);
		HeightMapChunk hmc = new HeightMapChunk(chunk);
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				hmc.setHeight(
					Coordinate
					.fromSnapshot(snapshot)
					.zoomIn(Coordinate.OFFSET_BLOCK_CHUNK)
					.plus(
						new Coordinate(x, z, Coordinate.LEVEL_BLOCK)
					),
					
					TerrainHelper.getTerrainHeight(x, z, snapshot)
				);
			}
		}
		
		heightMap.setChunk(chunk, hmc);
		
	}

}
