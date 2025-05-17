package com.csee.swplus.mileage.profile.not_shared.repository;

import com.csee.swplus.mileage.profile.not_shared.domain.Info;
import com.csee.swplus.mileage.profile.not_shared.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileTeckStackRepository extends  JpaRepository<TechStack, Integer> {
    Optional<TechStack> findBySnum(String snum);
}
