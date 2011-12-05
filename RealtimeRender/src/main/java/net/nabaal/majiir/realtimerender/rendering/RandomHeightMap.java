package net.nabaal.majiir.realtimerender.rendering;

import net.nabaal.majiir.realtimerender.Coordinate;

public class RandomHeightMap implements HeightMap { 
	
	@Override
	public int getHeight(Coordinate point) {
		if ((point.getX() % 32) > 16) {
			return 60 + (point.getY() % 32);
		} else {
			return 70 + (point.getY() % 32);
		}
	}

	@Override
	public void setHeight(Coordinate point, int height) {
		throw new UnsupportedOperationException();
	}

}
