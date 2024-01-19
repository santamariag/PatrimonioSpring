package eu.tasgroup.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.tasgroup.redis.model.Student;

@Repository
public interface StudentRepository  extends CrudRepository<Student, String>{

}
