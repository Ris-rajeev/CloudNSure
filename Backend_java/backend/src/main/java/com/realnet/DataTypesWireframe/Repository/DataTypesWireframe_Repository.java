package com.realnet.DataTypesWireframe.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.DataTypesWireframe.Entity.DataTypesWireframe_t;

@Repository
public interface  DataTypesWireframe_Repository  extends  JpaRepository<DataTypesWireframe_t, Long>  { 
}