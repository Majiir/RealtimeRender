package net.nabaal.majiir.realtimerender;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CoordinateTest {
	@Test
	public void compareTo() {
		Coordinate[] data = {
			new Coordinate(16, 0, 3),
			new Coordinate(6, 8, -2),
			new Coordinate(-12, -2, 4),
			new Coordinate(-3, 5, 0),
			new Coordinate(7, -5, 3),
			new Coordinate(4, 20, 3),
		};
		
		Arrays.sort(data);
  
		Coordinate[] sorted = {
			new Coordinate(6, 8, -2),
			new Coordinate(-3, 5, 0),
			new Coordinate(4, 20, 3),
			new Coordinate(7, -5, 3),
			new Coordinate(16, 0, 3),
			new Coordinate(-12, -2, 4),
		};
		  
		Assert.assertEquals(data, sorted);
	}
}
