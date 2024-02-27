package eu.tasgroup.redis.repository.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import eu.tasgroup.redis.repository.CustomTechMessageRepository;

@Repository
public class CustomTechMessageRepositoryImpl implements CustomTechMessageRepository {

	private final RedisTemplate<?, ?> template;
	
	private final ValueOperations<String, String> valueOperation;
	
	@Value("${keyspace.tech.ttl:60s}")
	private Duration ttl;
	
	public CustomTechMessageRepositoryImpl(RedisTemplate<String, String> template) {
		this.template = template;
		this.valueOperation = template.opsForValue();
	}

	@Override
	public void addKey(String key) {
		
		valueOperation.set(key, "", ttl);
		
	}

	@Override
	public boolean existKey(String key) {
		 
		return valueOperation.get(key)!=null
				 ? true
						 : false;
	}

}
