package com.csee.swplus.mileage.scholarship.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_sw_student")
public class Scholarship {
    @Id
    private String studentId;

    @Column(name = "is_apply")
    private Integer isApply;

    public Integer getIsApply() { // ✅ 직접 Getter 추가
        return isApply;
    }
}