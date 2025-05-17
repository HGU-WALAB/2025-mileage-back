package com.csee.swplus.mileage.profile.not_shared.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_sw_mileage_profile_techstack")
@Getter
@Setter
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String stacks;

    private String snum;

    public TechStack(String snum, String stacks) {
        this.snum = snum;
        this.stacks = stacks;
    }

    protected TechStack() {}

}