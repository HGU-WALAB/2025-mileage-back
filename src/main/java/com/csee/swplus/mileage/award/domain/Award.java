package com.csee.swplus.mileage.award.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "_sw_mileage_award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String snum;

    @Column(name = "award_date")
    private LocalDate awardDate;

    @Column(name = "award_year")
    private String awardYear;

    @Column(name = "contest_name")
    private String contestName;

    @Column(name = "award_name")
    private String awardName;

    @Column(name = "award_type")
    private String awardType;

    @Column(name = "organization")
    private String organization;
}
