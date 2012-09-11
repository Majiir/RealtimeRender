package net.nabaal.majiir.realtimerender;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class RealtimeRenderWorldListener implements Listener {

	private final RealtimeRender plugin; 
	
	public RealtimeRenderWorldListener(RealtimeRender instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkUnload(ChunkUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			plugin.getChunkManager().enqueue(event.getChunk());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onWorldUnload(WorldUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			plugin.enqueueLoadedChunks();
		}
	}
	
}
