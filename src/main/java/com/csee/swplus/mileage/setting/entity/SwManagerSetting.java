package com.csee.swplus.mileage.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "_sw_manager_setting")  // 테이블명 명시
public class SwManagerSetting {
    @Id
    private Long id;

    // current-semester 필드 추가
    @Column(name = "current_semester", length = 20)
    private String currentSemester;

    // getter, setter
    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }
}