package com.realnet.MenuMaintainance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.MenuMaintainance.Entity.Rn_Menu_maintainance;
import com.realnet.SureConnect.Entities.Sure_Connect;
@Repository
public interface MenuRepository extends JpaRepository<Rn_Menu_maintainance, Integer>{

	Rn_Menu_maintainance findById(int id);
}
