package com.csee.swplus.mileage.subitem.repository;

import com.csee.swplus.mileage.etcSubitem.domain.EtcSubitem;
import com.csee.swplus.mileage.subitem.domain.Subitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SubitemRepository extends JpaRepository<Subitem, Integer>{
}
