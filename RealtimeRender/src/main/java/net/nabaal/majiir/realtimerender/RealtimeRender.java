package net.nabaal.majiir.realtimerender;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import net.nabaal.majiir.realtimerender.commit.PluginCommitProvider;
import net.nabaal.majiir.realtimerender.commit.RealtimeRenderCommitPlugin;
import net.nabaal.majiir.realtimerender.rendering.ChunkManager;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RealtimeRender extends JavaPlugin {
	
	// TODO: Plugin-specific logger
	private static final Logger log = Logger.getLogger("Minecraft");
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	
	private final RealtimeRenderWorldListener worldListener = new RealtimeRenderWorldListener(this);
	
	private final PluginCommitProvider commitProvider = new PluginCommitProvider();
	private final ChunkManager chunkManager = new ChunkManager();
	
	private World world;
	private int startDelay;
	private int intervalDelay;
	
	@Override
	public void onDisable() {	
		log.info(String.format("%s: rendering loaded chunks...", this.getDescription().getName()));
		
		// TODO: Some way for tasks to not run simultaneously by accident, yet ensure the chunk queue is empty before shutting down.
		new EnqueueAndRenderTask(this, true).run();
		
		log.info(String.format("%s: disabled.", this.getDescription().getName()));
	}

	@Override
	public void onEnable() {
		
		/* TODO: Begin configuration */
		world = this.getServer().getWorld("world");
		startDelay = 60; // in seconds
		intervalDelay = 120;
		/* TODO: End configuration */
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.CHUNK_UNLOAD, worldListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.WORLD_UNLOAD, worldListener, Event.Priority.Monitor, this);
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new EnqueueAndRenderTask(this, false), startDelay * 20, intervalDelay * 20);
		
		log.info(String.format("%s: enabled.", this.getDescription().getName()));
		
		if (this.getDataFolder().mkdir()) {
			log.info(String.format("%s: created plugin data directory.", this.getDescription().getName()));
		}
	}
	
	public void registerCommitPlugin(RealtimeRenderCommitPlugin commitPlugin) {
		commitProvider.registerProvider(commitPlugin);
		log.info(String.format("%s: registered commit plugin '%s'.", this.getDescription().getName(), commitPlugin.getDescription().getName()));
	}
	
	public ChunkManager getChunkManager() {
		return chunkManager;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void commit(Iterable<File> files) {
		commitProvider.commitFiles(files);
	}
		
	public static ExecutorService getExecutor() {
		return executor;
	}
	
	public static Logger getLogger() {
		return log;
	}

	private static ChunkSnapshot getChunkSnapshot(Chunk chunk) {
		return chunk.getChunkSnapshot(true, false, true);
	}
	
	public void enqueueChunk(Chunk chunk) {
		chunkManager.enqueue(getChunkSnapshot(chunk));
	}
	
	public void enqueueLoadedChunks() {
		for (Chunk chunk : world.getLoadedChunks()) {
			this.enqueueChunk(chunk);
		}
	}
	
	// TODO TODO
	// Images with alpha channel (catch-all magenta for chunks?)
	// Zoom 'in' (textured blocks?)
	// Purge command
	// Separate commit-to-file and commit-to-webserver timers
	// Statistics (per-session)
	// Height shading
	// Depth shading
	// Color map (incl data values/IDs)
	// Biome shade adjustment (the colors are gaudy; make them more reasonable)
	// Different leaf types
	// (Semi-)transparent blocks
	// Proper block detection (air, flowers, structures, torches, longgrass, flowing water)
	// Flowing water whiteness/sparkle
	// Light shading
	
	// Precomputed map:
	// * blue = terrain height
	// * green = tree canopy height  
	// * red = light levels (with bit indicating 'source')
	
	// TODO: Test
	// * Are only changed files committed?
	// * Are files only accessed when necessary?
	// * Any memory leaks?

}
