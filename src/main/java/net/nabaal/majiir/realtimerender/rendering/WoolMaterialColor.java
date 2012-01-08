package net.nabaal.majiir.realtimerender.rendering;

import java.awt.Color;

public class WoolMaterialColor extends MetadataMaterialColor {

	private static final Color[] colors = new Color[] {
		new Color(0xdcdcdc), // white
		new Color(0xe77e34), // orange
		new Color(0xc050c8), // magenta
		new Color(0x6084c2), // light blue
		new Color(0xbdb520), // yellow
		new Color(0x43b428), // lime
		new Color(0xcf7a95), // pink
		new Color(0x424545), // gray
		new Color(0x9da3a3), // light gray
		new Color(0x2b729d), // cyan
		new Color(0x7335c2), // purple
		new Color(0x2a379b), // blue
		new Color(0x5f3a23), // brown
		new Color(0x3a5a29), // green
		new Color(0x9f2f28), // red
		new Color(0x241819), // black	
	};

	@Override
	public Color getColor(int data) {
		data %= 16;
		if (data < 0) { data += 16; }
		return colors[data];
	}

}
