package com.realnet.test2.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.test2.Entity.Gtest2;

@Repository
public interface  Gtest2Repository  extends  JpaRepository<Gtest2, Integer>  { 
}