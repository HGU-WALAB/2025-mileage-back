package com.csee.swplus.mileage.profile.not_shared.repository;

import com.csee.swplus.mileage.profile.not_shared.domain.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileInfoRepository extends JpaRepository<Info, Integer> {
    Optional<Info> findBySnum(String snum);
}
