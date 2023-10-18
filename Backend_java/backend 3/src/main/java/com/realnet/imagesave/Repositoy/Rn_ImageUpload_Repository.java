package com.realnet.imagesave.Repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.imagesave.entity.Rn_ImageUpload_Details;

public interface Rn_ImageUpload_Repository extends JpaRepository<Rn_ImageUpload_Details, Integer> {

}
