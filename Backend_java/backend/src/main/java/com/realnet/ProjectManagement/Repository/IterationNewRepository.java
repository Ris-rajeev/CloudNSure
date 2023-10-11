package com.realnet.ProjectManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Entity.Pm_Iteration;
@Repository
public interface IterationNewRepository extends JpaRepository<Pm_Iteration, Integer> {
	Optional<Pm_Iteration> findById(int id);

}
