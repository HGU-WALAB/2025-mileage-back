package com.csee.swplus.mileage.profile.domain;

import javax.persistence.*;

import com.csee.swplus.mileage.archive.project.domain.Project;
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

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "snum")
    private String snum;

    public ProfileProject(String snum, Integer projectId) {
        this.snum = snum;
        this.projectId = projectId;
    }

    protected ProfileProject() {
    }
}