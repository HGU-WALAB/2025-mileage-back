package com.csee.swplus.mileage.scholarship.repository;

import com.csee.swplus.mileage.scholarship.domain.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, String> {
    Optional<Scholarship> findByStudentId(String studentId);
}