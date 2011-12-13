package net.nabaal.majiir.realtimerender;

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
				e.printStackTrace();
			} 
		} else {
			thread.setPriority(Thread.MIN_PRIORITY);
		}
	}

}
