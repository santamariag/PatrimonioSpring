package eu.tasgroup.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.tasgroup.redis.service.DeduplicationService;

@RestController
@RequestMapping("/deduplication")
public class DeduplicationController {
	
	private final DeduplicationService service;

	public DeduplicationController(DeduplicationService service) {
		this.service = service;
	}
	
	@PostMapping
	public void add(@RequestBody String messageId) {
		
		service.add(messageId);
	}
	
	@GetMapping
	public boolean isDuplicated(@RequestParam String messageId) {
		
		return service.isDuplicated(messageId);
	}

}
