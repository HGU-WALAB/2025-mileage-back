package com.csee.swplus.mileage.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csee.swplus.mileage.user.entity.StudentSchool;

import java.util.Optional;

public interface StudentSchoolRepository extends JpaRepository<StudentSchool, Long> {
    Optional<StudentSchool> findBySchoolAndMajor1AndMajor2(String school, String major1, String major2);
}