package net.nabaal.majiir.realtimerender.palette;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.nabaal.majiir.realtimerender.RealtimeRender;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class YamlColorPalette implements ColorPalette {
	
	private final Map<Material, MaterialColor> colors = new HashMap<Material, MaterialColor>();

	public YamlColorPalette(ConfigurationSection config) {
		if (config == null) {
			RealtimeRender.getPluginLogger().warning("Couldn't find palette configuration section");
			return;
		}
		for (Entry<String, Object> entry : config.getValues(false).entrySet()) {
			Material material;
			String key = entry.getKey();
			if ((material = Material.matchMaterial(key)) == null) {
				if ((material = Material.getMaterial(Integer.valueOf(key))) == null) {
					RealtimeRender.getPluginLogger().warning("Couldn't find Material matching key: " + key);
					continue;
				}
			}
			if (!(entry.getValue() instanceof Integer)) {
				RealtimeRender.getPluginLogger().warning("Bad value for Material: " + material);
				continue;
			}
			colors.put(material, new SimpleMaterialColor(new Color((Integer)entry.getValue())));
		}
	}
	
	@Override
	public MaterialColor getMaterialColor(Material material) {
		return colors.get(material);
	}

}
