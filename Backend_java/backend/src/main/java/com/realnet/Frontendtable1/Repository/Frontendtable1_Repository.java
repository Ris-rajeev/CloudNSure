package com.realnet.Frontendtable1.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.Frontendtable1.Entity.Frontendtable1_t;

@Repository
public interface  Frontendtable1_Repository  extends  JpaRepository<Frontendtable1_t, Long>  { 
}