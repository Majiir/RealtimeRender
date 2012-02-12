package net.nabaal.majiir.realtimerender.commit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginCommitProvider implements CommitProvider {
	
	private final List<CommitProvider> providers = new ArrayList<CommitProvider>();

	public void registerProvider(CommitProvider provider) {
		providers.add(provider);
	}
	
	@Override
	public void commitFiles(Iterable<File> files, String dir) {
		for (CommitProvider provider : providers) {
			provider.commitFiles(files, dir);
		}
	}

}
