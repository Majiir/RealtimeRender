package net.nabaal.majiir.realtimerender;

import java.io.FileNotFoundException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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
		
		Thread thread = new Thread(new RenderTask(plugin));
		thread.start();
		
		if (wait) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO: Warn and dump chunk list upon exception? Or store "in progress" chunk list on filesystem?
				e.printStackTrace();
			} 
		} else {
			thread.setPriority(Thread.MIN_PRIORITY);
		}
	}

}
