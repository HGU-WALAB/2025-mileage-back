package com.csee.swplus.mileage.setting.repository;

import com.csee.swplus.mileage.setting.entity.Manager;
import com.csee.swplus.mileage.setting.entity.SwManagerSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
    Optional<Manager> findById(Long id);
}
