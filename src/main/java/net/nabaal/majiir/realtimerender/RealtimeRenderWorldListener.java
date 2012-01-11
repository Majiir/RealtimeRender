package net.nabaal.majiir.realtimerender;

import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldUnloadEvent;

public class RealtimeRenderWorldListener extends WorldListener {

	private final RealtimeRender plugin; 
	
	private int count = 0;
	private long tick;
	
	public RealtimeRenderWorldListener(RealtimeRender instance) {
		plugin = instance;
		tick = plugin.getWorld().getFullTime();
	}
	
	public void onChunkUnload(ChunkUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			if (tick != plugin.getWorld().getFullTime()) {
				count = 0;
				tick = plugin.getWorld().getFullTime();
			}
			if (count < plugin.getChunksUnloadPerTick()) {
				plugin.enqueueChunk(event.getChunk());
				count++;
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	public void onWorldUnload(WorldUnloadEvent event) {
		if (event.getWorld().equals(plugin.getWorld())) {
			plugin.enqueueLoadedChunks();
		}
	}
	
}
