package eu.tasgroup.redis.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;

import eu.tasgroup.redis.model.Student;
import eu.tasgroup.redis.repository.CustomStudentRepository;

public class CustomStudentRepositoryImpl implements CustomStudentRepository {

	@Autowired
	private RedisKeyValueTemplate template;

	
	@Override
	public void saveStudent(Student student) {
		
		template.insert(student);

	}

}
