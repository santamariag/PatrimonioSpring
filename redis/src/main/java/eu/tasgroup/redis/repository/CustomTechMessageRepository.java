package eu.tasgroup.redis.repository;

public interface CustomTechMessageRepository {
	
	public void addKey(String key);
	
	public boolean existKey(String key);

}
