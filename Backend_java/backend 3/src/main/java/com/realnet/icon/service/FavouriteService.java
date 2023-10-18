package com.realnet.icon.service;

import java.util.List;

import com.realnet.icon.entity.FavouriteEntity;

public interface FavouriteService {

	 public FavouriteEntity getById(int id);
     FavouriteEntity save(FavouriteEntity favourite) ;
     boolean deleteById(int id);
     public int getFavCount(int projectId);
     public boolean IsFav(int projectId, Long userId);
     List<FavouriteEntity> getAllByCreatedBy(Long created_by);
}
