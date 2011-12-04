package net.nabaal.majiir.realtimerender.commit;

public interface CommitProviderFactory<T extends CommitProvider> {	
	public T getNewProvider(String config);
}
