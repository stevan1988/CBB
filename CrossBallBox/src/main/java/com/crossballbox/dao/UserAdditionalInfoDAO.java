package com.crossballbox.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.UserAdditionalInfo;

public interface UserAdditionalInfoDAO extends JpaRepository<UserAdditionalInfo, Integer> {

}
