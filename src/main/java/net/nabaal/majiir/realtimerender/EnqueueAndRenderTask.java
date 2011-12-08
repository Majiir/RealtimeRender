package net.nabaal.majiir.realtimerender;

import java.util.concurrent.Future;

public class EnqueueAndRenderTask implements Runnable {

	private final RealtimeRender plugin;
	private final boolean wait;
	
	public EnqueueAndRenderTask(RealtimeRender plugin, boolean wait) {
		this.plugin = plugin;
		this.wait = wait;
	}
	
	@Override
	public void run() {
		plugin.enqueueLoadedChunks();
		
		Future<?> f = RealtimeRender.getExecutor().submit(new RenderTask(plugin));
		
		if (wait) {
			try {
				f.get();
			} catch (Exception e) {
				// TODO: Warn and dump chunk list upon exception? Or store "in progress" chunk list on filesystem?
				e.printStackTrace();
			} 
		} else {
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		}
	}

}
