package net.nabaal.majiir.realtimerender.commit;

import java.io.File;

public interface CommitProvider {
	public void commitFiles(Iterable<File> files, String dir);
	// TODO: public void purge();
}
