package com.csee.swplus.mileage.archive.award.repository;

import com.csee.swplus.mileage.archive.award.domain.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {
}