package com.csee.swplus.mileage.profile.not_shared.repository;

import com.csee.swplus.mileage.profile.not_shared.domain.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileInfoRepository extends JpaRepository<Info, Integer> {
}
