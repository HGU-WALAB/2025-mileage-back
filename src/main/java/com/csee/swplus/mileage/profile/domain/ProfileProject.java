package com.csee.swplus.mileage.profile.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_sw_mileage_profile_project")
@Getter
@Setter
public class ProfileProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Project _sw_mileage_project;

    private String snum;

    public ProfileProject(String snum, Project _sw_mileage_project) {
        this.snum = snum;
        this._sw_mileage_project = _sw_mileage_project;
    }
*/
    protected ProfileProject() {}
}