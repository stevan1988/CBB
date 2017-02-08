package com.crossballbox.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crossballbox.model.TrainingProgram;

public interface TrainingProgramDAO extends JpaRepository<TrainingProgram, Integer> {

	TrainingProgram findById(int id);

	TrainingProgram getTrainingProgramByName(String name);
	
}
