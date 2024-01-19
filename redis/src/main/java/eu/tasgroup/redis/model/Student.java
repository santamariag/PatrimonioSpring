package eu.tasgroup.redis.model;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;

@RedisHash(value = "Student")
@Data
public class Student implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2956078215650157412L;
	public enum Gender { 
        MALE, FEMALE
    }

    private String id;
    private String name;
    private Gender gender;
    private int grade;
    
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeToLive;
    
   
}
