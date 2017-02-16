package com.crossballbox.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.TrainingProgram;
import com.crossballbox.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	User findById(int id);
	
	User findUserByToken(String token);
	
	User getUserByUsername(String username);
	
	List<User> findUsersByFirstNameContaining(String firstName);
	
	List<User> findUsersByLastNameContaining(String lastName);
	
	List<User> getUsersByFirstName(String firstName);
	
	List<User> getUsersByLastName(String lastName);
	
	List<User> findAll();
	
//	List<User> getUsersByTrainings(TrainingProgram trainings);

}
