package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

public abstract class HeightMap {

	public static final byte NO_HEIGHT_INFORMATION = -1; 
	
	/**
	 * Gets the height at a point.
	 * @param point Point to get height at
	 * @return Height of highest point, or {@link NO_HEIGHT_INFORMATION} if no height information exists at that point.
	 */
	public abstract byte getHeight(Coordinate point);
	
	/**
	 * Sets the height at a point.
	 * @param point Point to set height at
	 * @param height Height level (0-127 or {@link NO_HEIGHT_INFORMATION})
	 */
	public abstract void setHeight(Coordinate point, byte height);
	
	/**
	 * Determines whether a height value is valid. Returns false for {@link NO_HEIGHT_INFORMATION}.
	 * @param height Height value to test
	 * @return Whether the height is valid
	 */
	public static boolean isValid(byte height) {
		return !(height < 0);
	}
	
}
