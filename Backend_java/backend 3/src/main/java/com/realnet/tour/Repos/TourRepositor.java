package com.realnet.tour.Repos;

import org.checkerframework.common.util.report.qual.ReportCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.tour.Entity.Tour;

@Repository
public interface TourRepositor extends JpaRepository<Tour, Integer>{

}
