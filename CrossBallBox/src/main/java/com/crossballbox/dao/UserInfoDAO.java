package com.crossballbox.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crossballbox.model.Programs;
import com.crossballbox.model.UserInfo;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInfo, Integer> {

	UserInfo findById(int id);
	
	@Modifying
	@Transactional
	@Query("update UserInfo u set u.imagePath = ?1 where u.id = ?2")
	int setImageForUser(String imagePath, int id);
	
	List<UserInfo> findUsersByTraining(Programs trainings);
	
//	List<UserProgress> getUserProgress
	
//	@Query("SELECT t FROM user_progress t where t.user_id = ?1")
//  public List<UserProgress> getUserProgressByUserInfoID(int id);
	
//	void updateImageById(byte[] image, int id);
	
//	void save(UserInfo userInfo, int id);
	
//	void saveImageById(byte[] image, int id);
	
//	UserInfo findUserByToken(String token);
//	
//	UserInfo getUserByUsername(String username);
//	
//	List<UserInfo> findUsersByFirstNameContaining(String firstName);
//	
//	List<UserInfo> findUsersByLastNameContaining(String lastName);
//	
//	List<UserInfo> getUsersByFirstName(String firstName);
//	
//	List<UserInfo> getUsersByLastName(String lastName);
//	
//	List<UserInfo> findAll();
}
