package com.realnet.Check.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.Check.Entity.Check_t;

@Repository
public interface  Check_Repository  extends  JpaRepository<Check_t, Long>  { 
}