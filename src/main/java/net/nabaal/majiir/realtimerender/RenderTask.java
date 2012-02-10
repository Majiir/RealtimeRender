package net.nabaal.majiir.realtimerender;

import net.nabaal.majiir.realtimerender.image.ChangeFilter;
import net.nabaal.majiir.realtimerender.image.ChunkRenderer;
import net.nabaal.majiir.realtimerender.image.CompositeImageBuilder;
import net.nabaal.majiir.realtimerender.image.FileImageProvider;
import net.nabaal.majiir.realtimerender.image.ImageProvider;
import net.nabaal.majiir.realtimerender.image.ImageReadCache;
import net.nabaal.majiir.realtimerender.image.ImageWriteCache;
import net.nabaal.majiir.realtimerender.image.ImageWriteMonitor;
import net.nabaal.majiir.realtimerender.image.ReadCache;
import net.nabaal.majiir.realtimerender.image.WriteCache;
import net.nabaal.majiir.realtimerender.image.ZoomImageBuilder;
import net.nabaal.majiir.realtimerender.palette.DefaultColorPalette;
import net.nabaal.majiir.realtimerender.rendering.AdaptiveNormalMap;
import net.nabaal.majiir.realtimerender.rendering.AverageNormalMap;
import net.nabaal.majiir.realtimerender.rendering.NormalMapReadCache;
import net.nabaal.majiir.realtimerender.rendering.CircleAverageNormalMap;
import net.nabaal.majiir.realtimerender.rendering.DiffuseShadedChunkRenderer;
import net.nabaal.majiir.realtimerender.rendering.FileHeightMap;
import net.nabaal.majiir.realtimerender.rendering.FiniteDifferencesNormalMap;
import net.nabaal.majiir.realtimerender.rendering.HeightMap;
import net.nabaal.majiir.realtimerender.rendering.HeightMapReadCache;
import net.nabaal.majiir.realtimerender.rendering.HeightMapRenderer;
import net.nabaal.majiir.realtimerender.rendering.HeightMapWriteCache;
import net.nabaal.majiir.realtimerender.rendering.NormalMap;
import net.nabaal.majiir.realtimerender.rendering.SerializedHeightMapFilePattern;
import net.nabaal.majiir.realtimerender.rendering.StructureMapRenderer;
import net.nabaal.majiir.realtimerender.rendering.TileFilePattern;

public class RenderTask implements Runnable {

	private final RealtimeRender plugin;
	
	public RenderTask(RealtimeRender plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		
		FileImageProvider fp;
		ImageProvider ip;
		ImageReadCache rc;
		ImageWriteCache wc;
		ImageWriteMonitor wm;
		HeightMap hm;
		ChunkRenderer renderer;
		
		plugin.getChunkManager().startBatch();
		
		// STAGE ONE: PREPROCESS (HEIGHT MAP)
		hm = new FileHeightMap(plugin.getDataFolder(), new SerializedHeightMapFilePattern(plugin.getDataFolder(), plugin.getWorld().getName(), Coordinate.LEVEL_TILE), Coordinate.SIZE_TILE);
		hm = new HeightMapReadCache(hm, Coordinate.SIZE_TILE);
		ReadCache hm_rc0 = (ReadCache) hm;
		hm = new HeightMapWriteCache(hm, Coordinate.SIZE_TILE);
		WriteCache hm_wc0 = (WriteCache) hm;
		hm = new HeightMapReadCache(hm, Coordinate.SIZE_CHUNK);
		ReadCache hm_rc1 = (ReadCache) hm;
		hm = new HeightMapWriteCache(hm, Coordinate.SIZE_CHUNK);
		WriteCache hm_wc1 = (WriteCache) hm;
		
		renderer = new HeightMapRenderer(hm);
		
		plugin.getChunkManager().render(renderer);
		hm_wc1.commit();
		hm_wc0.commit();
		
		// STAGE ONE: PREPROCESS (STRUCTURE MAP)
		HeightMap hms = new FileHeightMap(plugin.getDataFolder(), new SerializedHeightMapFilePattern(plugin.getDataFolder(), plugin.getWorld().getName() + ".s", Coordinate.LEVEL_TILE), Coordinate.SIZE_TILE);
		hms = new HeightMapReadCache(hms, Coordinate.SIZE_TILE);
		ReadCache hms_rc0 = (ReadCache) hms;
		hms = new HeightMapWriteCache(hms, Coordinate.SIZE_TILE);
		WriteCache hms_wc0 = (WriteCache) hms;
		hms = new HeightMapReadCache(hms, Coordinate.SIZE_CHUNK);
		ReadCache hms_rc1 = (ReadCache) hms;
		hms = new HeightMapWriteCache(hms, Coordinate.SIZE_CHUNK);
		WriteCache hms_wc1 = (WriteCache) hms;
		
		renderer = new HeightMapRenderer(hms);
		
		plugin.getChunkManager().render(renderer);
		hms_wc1.commit();
		hms_wc0.commit();
		
		// STAGE TWO: DRAWING
				
		// normal map
		NormalMap nm0 = new FiniteDifferencesNormalMap(hm);
		nm0 = new NormalMapReadCache(nm0);
		ReadCache nm_rc = (ReadCache) nm0;
		NormalMap nm = new CircleAverageNormalMap(nm0, 2);
		nm = new AdaptiveNormalMap(nm, nm0);
		
		// structure normal map
		NormalMap nms = new FiniteDifferencesNormalMap(hms);
		nms = new NormalMapReadCache(nms);
		ReadCache nms_rc = (ReadCache) nms;
		nms = new AverageNormalMap(nms, nm);
		
		// renders
		fp = new FileImageProvider(plugin.getDataFolder(), new TileFilePattern(plugin.getDataFolder(), plugin.getWorld().getName()));
		ip = fp;
		rc = new ImageReadCache(ip); 
		ip = rc;
		wm = new ImageWriteMonitor(ip);
		ip = wm;
		wc = new ImageWriteCache(ip);
		ip = wc;
		ip = new CompositeImageBuilder(ip, Coordinate.OFFSET_CHUNK_TILE);
		ip = new ChangeFilter(ip);
		ImageWriteCache wc1 = new ImageWriteCache(ip);
		ip = wc1;
		
		renderer = new DiffuseShadedChunkRenderer(ip, nm, nms, new DefaultColorPalette());
		
		// zoom out
		int zoomsOut = plugin.getZoomsOut();
		WriteCache[] caches = new WriteCache[zoomsOut];
		ip = rc;
		for (int i = zoomsOut; i > 0; i--) {
			if (ip != rc) {
				ImageWriteMonitor zwm = new ImageWriteMonitor(rc);
				zwm.addListener(ip);
				ip = zwm;
			}
			ImageWriteCache zwc = new ImageWriteCache(ip);
			caches[i - 1] = zwc;
			ip = zwc;
			ip = new CompositeImageBuilder(ip, 1);
			ip = new ZoomImageBuilder(ip, 1);
		}		
		wm.addListener(ip);
		
		// zoom in		
		ip = rc;
		for (int i = 0; i < plugin.getZoomsIn(); i++) {
			if (ip != rc) {
				ImageWriteMonitor zwm = new ImageWriteMonitor(rc);
				zwm.addListener(ip);
				ip = zwm;
			}
			ip = new CompositeImageBuilder(ip, -1);
			ip = new ZoomImageBuilder(ip, -1);
		}
		wm.addListener(ip);
	
		plugin.getChunkManager().render(renderer);
		plugin.getChunkManager().endBatch();
		nm_rc.clear();
		nms_rc.clear();
		hm_rc0.clear();
		hm_rc1.clear();
		hms_rc0.clear();
		hms_rc1.clear();
		wc1.commit();
		wc.commit();
		if (plugin.getRedoZooms()) {
			RealtimeRender.getLogger().info("RealtimeRender: zooms: rebuilding...");
			for (Coordinate tile : fp.getTiles()) {
				if (tile.getLevel() == Coordinate.LEVEL_TILE) {
					wc.setImage(tile, rc.getImage(tile));
				}
			}
			wc.commit();
			plugin.setRedoZooms(false);
			RealtimeRender.getLogger().info("RealtimeRender: zooms: done!");
		}
		for (WriteCache cache : caches) {
			cache.commit();
		}
		plugin.commit(fp.getChanged());
		
	}

}
