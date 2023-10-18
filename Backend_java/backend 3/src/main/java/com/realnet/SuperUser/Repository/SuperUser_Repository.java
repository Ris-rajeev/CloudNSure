package com.realnet.SuperUser.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.SuperUser.Entity.SuperUser_t;

@Repository
public interface  SuperUser_Repository  extends  JpaRepository<SuperUser_t, Long>  { 
}