package com.crossballbox.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crossballbox.model.UserInfo;

@Repository
//@Transactional(readOnly = true)
public interface UserInfoDAO extends JpaRepository<UserInfo, Integer> {

	UserInfo findById(int id);
	
	@Modifying
	@Transactional
	@Query("update UserInfo u set u.imagePath = ?1 where u.id = ?2")
	int setImageForUser(String imagePath, int id);
	
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
