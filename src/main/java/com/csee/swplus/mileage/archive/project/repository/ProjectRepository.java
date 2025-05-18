package com.csee.swplus.mileage.archive.project.repository;

import com.csee.swplus.mileage.archive.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}