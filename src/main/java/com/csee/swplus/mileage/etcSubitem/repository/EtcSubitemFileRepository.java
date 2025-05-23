package com.csee.swplus.mileage.etcSubitem.repository;

import com.csee.swplus.mileage.etcSubitem.domain.EtcSubitemFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtcSubitemFileRepository extends JpaRepository<EtcSubitemFile, Integer> {
    List<EtcSubitemFile> findByRecordId(int recordId);
    EtcSubitemFile findById(int id);
    void deleteByRecordId(int recordId);
}
