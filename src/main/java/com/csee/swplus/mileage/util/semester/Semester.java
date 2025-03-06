package com.csee.swplus.mileage.util.semester;

import javax.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "_sw_manager_setting")
public class Semester {
    @Id
    private int id;

    @Column(name = "current_semester")
    private String currentSemester; // "2024-01" 형태
}
