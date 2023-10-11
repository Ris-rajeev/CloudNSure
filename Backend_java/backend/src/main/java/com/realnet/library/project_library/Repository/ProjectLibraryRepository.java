package com.realnet.library.project_library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.library.project_library.Entity.ProjectLibrary;
@Repository
public interface ProjectLibraryRepository extends JpaRepository<ProjectLibrary, Integer>{

}
