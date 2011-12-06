package net.nabaal.majiir.realtimerender.commit;

import java.io.File;

import net.nabaal.majiir.realtimerender.RealtimeRender;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class RealtimeRenderCommitPlugin extends JavaPlugin implements CommitProvider {

	@Override
	public void onDisable() {
		// TODO: Unregister so this plugin can disable without impacting the main plugin
	}

	@Override
	public void onEnable() {
		((RealtimeRender)this.getServer().getPluginManager().getPlugin("RealtimeRender")).registerCommitPlugin(this);
	}

	@Override
	public abstract void commitFiles(Iterable<File> files);

}
