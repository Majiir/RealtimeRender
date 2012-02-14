package net.nabaal.majiir.realtimerender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarkerGroup {
	
	private final String label;
	private final List<Marker> markers = new ArrayList<Marker>();
	
	public MarkerGroup(String label, List<Marker> markers) {
		this.label = label;
		this.markers.addAll(markers);
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<Marker> getMarkers() {
		return Collections.unmodifiableList(markers);
	}
	
}
