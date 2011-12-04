package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.image.Coordinate;

public interface HeightMap {

	public static final int NO_HEIGHT_INFORMATION = 128; 
	
	/**
	 * Gets the height at a point.
	 * @param point Point to get height at
	 * @return Height of highest point, or {@link NO_HEIGHT_INFORMATION} if no height information exists at that point.
	 */
	public int getHeight(Coordinate point);
	
	/**
	 * Sets the height at a point.
	 * @param point Point to set height at
	 * @param height Height level (0-127 or {@link NO_HEIGHT_INFORMATION})
	 */
	public void setHeight(Coordinate point, int height);
	
}
