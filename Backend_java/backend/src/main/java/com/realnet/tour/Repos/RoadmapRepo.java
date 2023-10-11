package com.realnet.tour.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.tour.Entity.Roadmap;

@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap, Integer>{

}
