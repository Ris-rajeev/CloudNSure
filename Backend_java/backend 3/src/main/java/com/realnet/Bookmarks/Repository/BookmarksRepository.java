package com.realnet.Bookmarks.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Bookmarks.Entity.Bookmarks;

@Repository
public interface  BookmarksRepository  extends  JpaRepository<Bookmarks, Integer>  { 
}