package net.nabaal.majiir.realtimerender;

public class EnqueueAndRenderTask implements Runnable {

	private final RealtimeRender plugin;
	private final boolean wait;
	private final boolean renderLoaded;
	
	public EnqueueAndRenderTask(RealtimeRender plugin, boolean wait, boolean renderLoaded) {
		this.plugin = plugin;
		this.wait = wait;
		this.renderLoaded = renderLoaded;
	}
	
	@Override
	public void run() {
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
	}

}
