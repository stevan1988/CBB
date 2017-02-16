package com.crossballbox.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.UserHealthyState;

public interface UserHealthyStateDAO extends JpaRepository<UserHealthyState, Integer> {

}
