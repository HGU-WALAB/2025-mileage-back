package com.csee.swplus.mileage.util.semester;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "_sw_manager_setting")
public class Semester {
    @Id
    private int id;

    private String currentSemester; // "2024-01" 형태
}
