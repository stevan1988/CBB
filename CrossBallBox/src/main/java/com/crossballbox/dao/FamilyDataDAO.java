package com.crossballbox.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.FamilyData;

public interface FamilyDataDAO extends JpaRepository<FamilyData, Integer> {

}
