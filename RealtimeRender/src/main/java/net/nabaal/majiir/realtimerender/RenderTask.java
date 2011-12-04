package net.nabaal.majiir.realtimerender;

import net.nabaal.majiir.realtimerender.image.ChunkRenderer;
import net.nabaal.majiir.realtimerender.image.CompositeImageBuilder;
import net.nabaal.majiir.realtimerender.image.Coordinate;
import net.nabaal.majiir.realtimerender.image.FileImageProvider;
import net.nabaal.majiir.realtimerender.image.ImageProvider;
import net.nabaal.majiir.realtimerender.image.ImageReadCache;
import net.nabaal.majiir.realtimerender.image.ImageWriteCache;
import net.nabaal.majiir.realtimerender.image.ImageWriteMonitor;
import net.nabaal.majiir.realtimerender.image.ZoomImageBuilder;
import net.nabaal.majiir.realtimerender.rendering.AdaptiveNormalMap;
import net.nabaal.majiir.realtimerender.rendering.CachedNormalMap;
import net.nabaal.majiir.realtimerender.rendering.CircleAverageNormalMap;
import net.nabaal.majiir.realtimerender.rendering.DiffuseShadedChunkRenderer;
import net.nabaal.majiir.realtimerender.rendering.FiniteDifferencesNormalMap;
import net.nabaal.majiir.realtimerender.rendering.HeightMap;
import net.nabaal.majiir.realtimerender.rendering.HeightMapFilePattern;
import net.nabaal.majiir.realtimerender.rendering.HeightMapRenderer;
import net.nabaal.majiir.realtimerender.rendering.ImageHeightMap;
import net.nabaal.majiir.realtimerender.rendering.NormalMap;
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
		
		fp = new FileImageProvider(plugin.getDataFolder(), new HeightMapFilePattern(plugin.getDataFolder(), plugin.getWorld().getName()));
		ip = fp;
		rc = new ImageReadCache(ip);
		ip = rc;
		//ip = new ChangeFilter(ip); // TODO: Test!
		wc = new ImageWriteCache(ip);
		ip = wc;
		ip = new CompositeImageBuilder(ip, Coordinate.OFFSET_CHUNK_TILE);
		//ip = new ImageNullMonitor(ip, Coordinate.SIZE_CHUNK);
		ip = new ImageReadCache(ip);
		ImageHeightMap ihm = new ImageHeightMap(ip);
		hm = ihm;
		
		renderer = new HeightMapRenderer(ihm);
		
		plugin.getChunkManager().render(renderer);
		wc.commit();
		
		// STAGE TWO: DRAWING
		
		// TODO: Improve
		
		// normal map
		//hm = new RandomHeightMap();
		NormalMap nm0 = new FiniteDifferencesNormalMap(hm);
		nm0 = new CachedNormalMap(nm0);
		NormalMap nm = new CircleAverageNormalMap(nm0, 2);
		nm = new AdaptiveNormalMap(nm, nm0);
		//NormalMap nm = nm0;
		
		// END TODO: Improve
		
		// renders
		fp = new FileImageProvider(plugin.getDataFolder(), new TileFilePattern(plugin.getDataFolder(), plugin.getWorld().getName()));
		ip = fp;
		rc = new ImageReadCache(ip); 
		ip = rc;
		wm = new ImageWriteMonitor(ip);
		ip = wm;
		//ip = new ChangeFilter(ip);
		wc = new ImageWriteCache(ip);
		ip = wc;
		ip = new CompositeImageBuilder(ip, Coordinate.OFFSET_CHUNK_TILE);
		
		renderer = new DiffuseShadedChunkRenderer(ip, nm);
		
		// zoom out
		ip = new CompositeImageBuilder(rc, 1);
		ip = new ZoomImageBuilder(ip, 1); // note the common "1"
		wm.addListener(ip);
		
		// zoom in
		ip = new CompositeImageBuilder(rc, -1);
		ip = new ZoomImageBuilder(ip, -1);
		wm.addListener(ip);
	
		plugin.getChunkManager().render(renderer);
		plugin.getChunkManager().endBatch();
		wc.commit();
		plugin.commit(fp.getChanged());
		
	}

}
