package net.nabaal.majiir.realtimerender.rendering;

import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;

public class ProcessedChunkSnapshot implements SerializableChunkSnapshot {

	private static final long serialVersionUID = 2L;
	
	private final byte[] blockData = new byte[65536];
	private final byte[] blockLight = new byte[65536];
	private final byte[] blockTypes = new byte[65536];
	private final Biome[] biome = new Biome[256];
	private final double[] biomeRainfall = new double[256];
	private final double[] biomeTemperature = new double[256];
	private final int[] heightMap = new int[256];
	private final boolean[] sections = new boolean[16];
	private final long captureFullTime;
	private final String worldName;
	private final int x;
	private final int z;
	
	public ProcessedChunkSnapshot(ChunkSnapshot snapshot) {
		this.captureFullTime = snapshot.getCaptureFullTime();
		this.worldName = snapshot.getWorldName();
		this.x = snapshot.getX();
		this.z = snapshot.getZ();
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				biome[getArrayIndex(x, z)] = snapshot.getBiome(x, z);
				biomeRainfall[getArrayIndex(x, z)] = snapshot.getRawBiomeRainfall(x, z);
				biomeTemperature[getArrayIndex(x, z)] = snapshot.getRawBiomeTemperature(x, z);
				heightMap[getArrayIndex(x, z)] = snapshot.getHighestBlockYAt(x, z);
				for (int y = 0; y < 256; y++) {
					blockData[getArrayIndex(x, y, z)] = intToByte(snapshot.getBlockData(x, y, z));
					blockLight[getArrayIndex(x, y, z)] = intToByte(snapshot.getBlockEmittedLight(x, y, z) + (snapshot.getBlockSkyLight(x, y, z) << 4));
					blockTypes[getArrayIndex(x, y, z)] = intToByte(snapshot.getBlockTypeId(x, y, z));
				}
			}
		}
		
		for (int sy = 0; sy < 16; sy++) {
			sections[sy] = snapshot.isSectionEmpty(sy);
		}
	}
	
	private static byte intToByte(int i) {
		return (byte) (i - 128);
	}
	
	private static int byteToInt(byte b) {
		return b + 128;
	}
	
	public ProcessedChunkSnapshot(int x, int z, String worldName, long captureFullTime, Biome[] biome, double[] biomeRainfall, double[] biomeTemperature, byte[] blocks, byte[] blockData, byte[] blockLight, byte[] blockTypes, int[] heightMap, boolean[] sections) {
		this.x = x;
		this.z = z;
		this.worldName = worldName;
		this.captureFullTime = captureFullTime;
		
		System.arraycopy(biome, 0, this.biome, 0, biome.length);
		System.arraycopy(biomeRainfall, 0, this.biomeRainfall, 0, biomeRainfall.length);
		System.arraycopy(biomeTemperature, 0, this.biomeTemperature, 0, biomeTemperature.length);
		System.arraycopy(blockData, 0, this.blockData, 0, blockData.length);
		System.arraycopy(blockLight, 0, this.blockLight, 0, blockLight.length);
		System.arraycopy(blockTypes, 0, this.blockTypes, 0, blockTypes.length);
		System.arraycopy(heightMap, 0, this.heightMap, 0, heightMap.length);
		System.arraycopy(sections, 0, this.sections, 0, sections.length);
	}
	
	@Override
	public Biome getBiome(int x, int z) {
		return biome[getArrayIndex(x, z)];
	}

	@Override
	public int getBlockData(int x, int y, int z) {
		return byteToInt(blockData[getArrayIndex(x, y, z)]);
	}

	@Override
	public int getBlockEmittedLight(int x, int y, int z) {
		return byteToInt(blockLight[getArrayIndex(x, y, z)]) & 0xF;
	}

	@Override
	public int getBlockSkyLight(int x, int y, int z) {
		return (byteToInt(blockLight[getArrayIndex(x, y, z)]) >> 4) & 0xF;
	}

	@Override
	public int getBlockTypeId(int x, int y, int z) {
		return byteToInt(blockTypes[getArrayIndex(x, y, z)]) & 0xFF;
	}

	@Override
	public long getCaptureFullTime() {
		return captureFullTime;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		return heightMap[getArrayIndex(x, z)];
	}

	@Override
	public double getRawBiomeRainfall(int x, int z) {
		return biomeRainfall[getArrayIndex(x, z)];
	}

	@Override
	public double getRawBiomeTemperature(int x, int z) {
		return biomeTemperature[getArrayIndex(x, z)];
	}

	@Override
	public String getWorldName() {
		return worldName;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getZ() {
		return z;
	}
	
	@Override
	public boolean isSectionEmpty(int sy) {
		return sections[sy];
	}
	
	private int getArrayIndex(int x, int z) {
		return (x << 4) + z;
	}
	
	private int getArrayIndex(int x, int y, int z) {
		return (getArrayIndex(x, z) << 8) + y;
	}

}
