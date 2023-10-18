package com.realnet.test2.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.test2.Entity.Gtest3;

@Repository
public interface  Gtest3Repository  extends  JpaRepository<Gtest3, Integer>  { 
}