package net.nabaal.majiir.realtimerender;

public class Marker {
	
	private final String label;
	private final Coordinate location;
	
	public Marker(String label, Coordinate location) {
		if (location.getLevel() != Coordinate.LEVEL_BLOCK) {
			throw new IllegalArgumentException();
		}
		this.label = label;
		this.location = location;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Coordinate getLocation() {
		return location;
	}
	
}
