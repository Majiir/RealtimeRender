package net.nabaal.majiir.realtimerender;

import java.util.concurrent.locks.Lock;

public class EnqueueAndRenderTask implements Runnable {

	private final RealtimeRender plugin;
	private final boolean wait;
	private final boolean renderLoaded;
	private final Lock lock;
	
	public EnqueueAndRenderTask(RealtimeRender plugin, boolean wait, boolean renderLoaded, Lock renderLock) {
		this.plugin = plugin;
		this.wait = wait;
		this.renderLoaded = renderLoaded;
		this.lock = renderLock;
	}
	
	@Override
	public void run() {
		if (!lock.tryLock()) {
			if (wait) {
				RealtimeRender.getLogger().info("RealtimeRender: waiting for render in progress to finish...");
				lock.lock();
			} else {
				RealtimeRender.getLogger().warning("RealtimeRender: render task already running, skipping this cycle.");
				return;
			}
		}
		if (wait) {
			lock.lock();
		} else {
			if (!lock.tryLock()) {
				return;
			}
		}
		try {
			if (renderLoaded) {
				plugin.enqueueLoadedChunks();
			}
			
			Thread thread = new Thread(new RenderTask(plugin));
			thread.start();
			
			if (wait) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			} else {
				thread.setPriority(Thread.MIN_PRIORITY);
			}
		} finally {
			lock.unlock();
		}
	}

}
