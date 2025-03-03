package com.csee.swplus.mileage.setting.repository;

import com.csee.swplus.mileage.setting.entity.SwManagerSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SwManagerSettingRepository extends JpaRepository<SwManagerSetting, Long> {
    Optional<SwManagerSetting> findById(Long id); // id로 조회하는 메서드 추가
}
