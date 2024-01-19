package eu.tasgroup.redis.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.tasgroup.redis.model.Student;
import eu.tasgroup.redis.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	
	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}
	
	
	@PostMapping
	public void addStudent(@RequestBody Student student) {
		
		service.saveStudent(student);
	}
	
	@DeleteMapping("/{id}")
	public void addStudent(@PathVariable String id) {
		
		service.deleteStudent(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> findStudent(@PathVariable String id){
		
		Optional<Student> studentOpt=service.getStudent(id);
		
		return studentOpt.isPresent()
				? ResponseEntity.ok(studentOpt.get())
						: ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Student>> findAll(){
		
		return ResponseEntity.ok(service.getStudents());
	}
	
}
