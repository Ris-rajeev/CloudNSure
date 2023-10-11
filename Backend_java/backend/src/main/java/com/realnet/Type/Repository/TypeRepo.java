package com.realnet.Type.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Type.entity.Sec_type;

@Repository
public interface TypeRepo extends JpaRepository<Sec_type,Integer>{

}
