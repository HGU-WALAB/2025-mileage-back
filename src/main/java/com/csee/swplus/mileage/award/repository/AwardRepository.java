package com.csee.swplus.mileage.award.repository;

import com.csee.swplus.mileage.award.domain.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {
}