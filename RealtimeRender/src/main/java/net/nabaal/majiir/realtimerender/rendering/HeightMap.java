package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class HeightMap {

	public static final int NO_HEIGHT_INFORMATION = 128; 
	
	/**
	 * Gets the height at a point.
	 * @param point Point to get height at
	 * @return Height of highest point, or {@link NO_HEIGHT_INFORMATION} if no height information exists at that point.
	 */
	public abstract int getHeight(Coordinate point);
	
	/**
	 * Sets the height at a point.
	 * @param point Point to set height at
	 * @param height Height level (0-127 or {@link NO_HEIGHT_INFORMATION})
	 */
	public abstract void setHeight(Coordinate point, int height);
	
	/**
	 * Determines whether a height value is valid. Returns false for {@link NO_HEIGHT_INFORMATION}.
	 * @param height Height value to test
	 * @return Whether the height is valid
	 */
	public static boolean isValid(int height) {
		return !((height < 0) || (height > 127));
	}
	
}
