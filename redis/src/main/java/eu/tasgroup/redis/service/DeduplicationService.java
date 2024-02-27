package eu.tasgroup.redis.service;


import org.springframework.stereotype.Service;

import eu.tasgroup.redis.repository.CustomTechMessageRepository;

@Service
public class DeduplicationService {
	
	private final CustomTechMessageRepository repository;
	
	private static final String GPM_FOE_TECH_KEY_PREFIX="GPM_FOE_TECH_";
	
	public DeduplicationService(CustomTechMessageRepository repository) {
		this.repository = repository;
	}



	public boolean isDuplicated(String messageId) {
		
		return repository.existKey(GPM_FOE_TECH_KEY_PREFIX.concat(messageId));
	}
	
	public void add(String messageId) {
		
		repository.addKey(GPM_FOE_TECH_KEY_PREFIX.concat(messageId));
	}
	

}
