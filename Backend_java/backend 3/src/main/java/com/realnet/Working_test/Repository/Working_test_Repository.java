package com.realnet.Working_test.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.Working_test.Entity.Working_test_t;

@Repository
public interface  Working_test_Repository  extends  JpaRepository<Working_test_t, Long>  { 
}