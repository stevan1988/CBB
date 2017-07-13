package com.crossballbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.Programs;
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
	
	List<User> findUsersByRole(String role);
	
//	List<User> findUsersByRoleAndByUserInfoWhereTraining(String role, Programs training);
	
	
	
//	@Query("SELECT * FROM user u where u.role != ROLE_ADMIN") 
//	List<User> findUsersByRole();
	
//	@Query("SELECT * FROM user u where u.role != ROLE_ADMIN") 
//    List<User> findNonAdminUsersByFirstName(@Param("id") Long id);
//	
//	@Query("SELECT * FROM user u where u.role != ROLE_ADMIN") 
//    List<User> findNonAdminUsersByLastName(@Param("lastname") String lastname);
//	
//	@Query("SELECT t FROM Todo t where t.title = ?1 AND t.description = ?2")
//    public Optional<User> findByTitleAndDescription(String title, String description);
	
//	List<User> getUsersByTrainings(TrainingProgram trainings);
	
	     
//	    @Query("SELECT t.title FROM Todo t where t.id = :id") 
//	    Optional<String> findTitleById(@Param("id") Long id);

}
