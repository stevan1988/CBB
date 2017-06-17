package com.crossballbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;

  public interface UserProgressDAO extends JpaRepository<UserProgress, Integer> {

    UserProgress findById(int id);
    
    List<UserProgress> findByUserInfo(UserInfo userInfo);//called UserProgress - polje koje je foreign key
    
  }
