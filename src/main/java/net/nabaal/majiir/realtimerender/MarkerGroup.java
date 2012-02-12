package net.nabaal.majiir.realtimerender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarkerGroup {
	
	private final String label;
	private final List<Marker> markers = new ArrayList<Marker>();
	
	public MarkerGroup(String label, Coordinate location) {
		if (location.getLevel() != Coordinate.LEVEL_BLOCK) {
			throw new IllegalArgumentException();
		}
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<Marker> getMarkers() {
		return Collections.unmodifiableList(markers);
	}
	
}
