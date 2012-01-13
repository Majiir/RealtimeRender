package net.nabaal.majiir.realtimerender;

import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldUnloadEvent;

public class RealtimeRenderWorldListener extends WorldListener {

	private final RealtimeRender plugin; 
	
	public RealtimeRenderWorldListener(RealtimeRender instance) {
		plugin = instance;
	}
	
	public void onChunkUnload(ChunkUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			plugin.enqueueChunk(event.getChunk());
		}
	}
	
	public void onWorldUnload(WorldUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			plugin.enqueueLoadedChunks();
		}
	}
	
}
