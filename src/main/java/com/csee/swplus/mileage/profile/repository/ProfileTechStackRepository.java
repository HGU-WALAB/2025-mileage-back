package com.csee.swplus.mileage.profile.repository;

import com.csee.swplus.mileage.profile.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileTechStackRepository extends  JpaRepository<TechStack, Integer> {
    Optional<TechStack> findBySnum(String snum);
}
