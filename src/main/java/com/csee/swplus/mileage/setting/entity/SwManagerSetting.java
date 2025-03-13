package com.csee.swplus.mileage.setting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "_sw_manager_setting")
public class SwManagerSetting {
    @Id
    private Long id;

    @Column(name = "current_semester", length = 20)
    private String currentSemester;

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }
}