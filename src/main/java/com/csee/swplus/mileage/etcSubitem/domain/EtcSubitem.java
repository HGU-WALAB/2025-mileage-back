package com.csee.swplus.mileage.etcSubitem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "_sw_mileage_record")
public class EtcSubitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String semester;

    private int categoryId;

    private int subitemId;

    private String snum;

    private String sname;

    private int value;

    private int mPoint;

    private int extraPoint;

    private String description1;

    private String description2;

    @Column(insertable = false, updatable = false)
    private LocalDateTime moddate;

    @Column(insertable = false)
    private LocalDateTime regdate;
}
