package com.csee.swplus.mileage.profile.repository;

import com.csee.swplus.mileage.profile.domain.ProfileProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileProjectRepository extends JpaRepository<ProfileProject, Integer> {
    ProfileProject findBySnum(String snum);

}
