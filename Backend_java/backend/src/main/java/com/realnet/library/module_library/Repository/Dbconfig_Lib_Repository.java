package com.realnet.library.module_library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.library.module_library.Entity.Dbconfig_Lib;

@Repository
public interface Dbconfig_Lib_Repository extends JpaRepository<Dbconfig_Lib, Integer>{

}
