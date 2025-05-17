package com.csee.swplus.mileage.archive.award.domain;

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

    // 생성자
    public Award(String snum, LocalDate awardDate, String awardYear, String contestName, String awardName, String awardType, String organization) {
        this.snum = snum;
        this.awardDate = awardDate;
        this.awardYear = awardYear;
        this.contestName = contestName;
        this.awardName = awardName;
        this.awardType = awardType;
        this.organization = organization;
    }

    protected Award() {}
}
