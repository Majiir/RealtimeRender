package net.nabaal.majiir.realtimerender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import net.nabaal.majiir.realtimerender.commit.CommitProvider;
import net.nabaal.majiir.realtimerender.commit.PluginCommitProvider;
import net.nabaal.majiir.realtimerender.rendering.ChunkFilePattern;
import net.nabaal.majiir.realtimerender.rendering.ChunkManager;
import net.nabaal.majiir.realtimerender.rendering.FileChunkSnapshotProvider;
import net.nabaal.majiir.realtimerender.rendering.NoOpChunkPreprocessor;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import flexjson.JSONSerializer;

public class RealtimeRender extends JavaPlugin {
	
	// TODO: Plugin-specific logger
	private static final Logger log = Logger.getLogger("Minecraft");
	
	private final RealtimeRenderWorldListener worldListener = new RealtimeRenderWorldListener(this);
	
	private final PluginCommitProvider commitProvider = new PluginCommitProvider();
	private final Lock renderLock = new ReentrantLock();
	private ChunkManager chunkManager;
	private ChunkSaveTask chunkSaveTask;
	
	private World world;
	private int startDelay;
	private int intervalDelay;
	private int saveThreads;
	private boolean renderLoaded;
	private int zoomsIn;
	private int zoomsOut;
	
	private boolean redoZooms = false; 
	
	@Override
	public void onDisable() {	
		log.info(String.format("%s: rendering loaded chunks...", this.getDescription().getName()));
		
		// TODO: Some way for tasks to not run simultaneously by accident, yet ensure the chunk queue is empty before shutting down.
		new EnqueueAndRenderTask(this, true, true, renderLock).run();
		
		chunkSaveTask.stop();
		
		log.info(String.format("%s: disabled.", this.getDescription().getName()));
	}

	@Override
	public void onEnable() {
		
		if (this.getDataFolder().mkdir()) {
			log.info(String.format("%s: created plugin data directory.", this.getDescription().getName()));
		}
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			this.saveDefaultConfig();
		}
		
		Configuration config = this.getConfig();
		
		world = this.getServer().getWorld(config.getString("world"));
		startDelay = config.getInt("startDelay");
		intervalDelay = config.getInt("intervalDelay");
		saveThreads = config.getInt("saveThreads");
		renderLoaded = config.getBoolean("renderLoaded");
		zoomsIn = config.getInt("zoomsIn");
		zoomsOut = config.getInt("zoomsOut");
		
		chunkManager = new ChunkManager(new NoOpChunkPreprocessor(), new FileChunkSnapshotProvider(new ChunkFilePattern(this.getDataFolder(), "world")));
		chunkSaveTask = new ChunkSaveTask(chunkManager);
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.CHUNK_UNLOAD, worldListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.WORLD_UNLOAD, worldListener, Event.Priority.Monitor, this);
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new EnqueueAndRenderTask(this, false, renderLoaded, renderLock), startDelay * 20, intervalDelay * 20);
		for (int i = 0; i < saveThreads; i++) {
			this.getServer().getScheduler().scheduleAsyncDelayedTask(this, chunkSaveTask);
		}
		
		int interval = config.getInt("discoverInterval");
		
		if (interval > 0) {
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new DiscoverTerrainTask(this, 4.0), 100, interval);
		} else {
			log.warning("RealtimeRender: discover interval must be greater than zero. (check config.yml)");
		}
		
		getCommand("map").setExecutor(new CommandManager(this));
		
		File options = new File(getDataFolder(), "options.json");
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude("class");
		try {
			serializer.serialize(new Object() {
				public int minZoom = -1 * zoomsIn;
				public int maxZoom = zoomsOut;
				public Object spawn = new Object() {
					public int x = world.getSpawnLocation().getBlockX();
					public int y = world.getSpawnLocation().getBlockY();
				};
			}, new FileWriter(options));
		} catch (IOException e) {
			log.warning(String.format("%s: failed to write options file!", this.getDescription().getName()));
		}
		commitProvider.commitFiles(Arrays.asList(new File[] { options }));
		
		log.info(String.format("%s: enabled.", this.getDescription().getName()));
	}
	
	public void registerCommitPlugin(CommitProvider provider) {
		commitProvider.registerProvider(provider);
	}
	
	public ChunkManager getChunkManager() {
		return chunkManager;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public int getZoomsIn() {
		return zoomsIn;
	}
	
	public int getZoomsOut() {
		return zoomsOut;
	}
	
	public boolean getRedoZooms() {
		return redoZooms;
	}
	
	public void setRedoZooms(boolean redoZooms) {
		this.redoZooms = redoZooms;
	}
	
	public void commit(Iterable<File> files) {
		commitProvider.commitFiles(files);
	}
	
	public static Logger getLogger() {
		return log;
	}

	private static ChunkSnapshot getChunkSnapshot(Chunk chunk) {
		return chunk.getChunkSnapshot(true, true, true);
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
