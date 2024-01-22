package eu.tasgroup.redis.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.tasgroup.redis.model.Student;
import eu.tasgroup.redis.repository.StudentRepository;

@Service
public class StudentService {
	
	private final StudentRepository repository;
	@Value("${keyspace.student.ttl:60}")
	private Long ttl;

	public StudentService(StudentRepository repository) {
		this.repository = repository;
	}
	
	

	public void saveStudent(Student student) {
		
		student.setTimeToLive(ttl);
		repository.saveStudent(student);
	}
	
	public Optional<Student> getStudent(String id){
		
		return repository.findById(id);
	}
	
	public Iterable<Student> getStudents(){
		
		//List<Student> students = new ArrayList<>();
		return repository.findAll();//.forEach(students::add);
		//return students;
	}
	
	public void deleteStudent(String id){
		
		repository.deleteById(id);
	}
}
