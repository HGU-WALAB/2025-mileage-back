package com.csee.swplus.mileage.award.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "_sw_mileage_award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
