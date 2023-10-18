package com.realnet.icon.service;

import java.util.List;

import com.realnet.icon.entity.ArchivedEntity;

public interface ArchivedService {

	public ArchivedEntity getById(int id);
	ArchivedEntity save(ArchivedEntity archived) ;
//    boolean deleteById(ArchivedEntity archived);
    public boolean IsArchived(int projectId, Long userId);
    List<ArchivedEntity> getAllByUserId(Long created_by);
	public boolean delete(ArchivedEntity archivedEntity);
}
