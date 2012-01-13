package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.bukkit.block.Biome;

public class RandomTextureMaterialColor implements MaterialColor {
	
	private final BufferedImage texture;
	
	public RandomTextureMaterialColor(BufferedImage texture) {
		this.texture = texture;
	}

	@Override
	public final Color getColor(int data, int x, int z, double rainfall, double temperature, Biome biome) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md5.update(new byte[] {
				(byte)(x >>> 24),
				(byte)(x >>> 16),
				(byte)(x >>> 8),
				(byte)x,
				(byte)(z >>> 24),
				(byte)(z >>> 16),
				(byte)(z >>> 8),
				(byte)z
			}, 0, 8);
		byte[] digest = md5.digest();
		long seed = digest[0] + (digest[1] << 8) + (digest[2] << 16) + (digest[3] << 24) + (digest[4] << 32) + (digest[5] << 40) + (digest[6] << 48) + (digest[7] << 56);
		Random random = new Random(seed);
		return new Color(texture.getRGB(random.nextInt(texture.getWidth()), random.nextInt(texture.getHeight())));
	}

}
