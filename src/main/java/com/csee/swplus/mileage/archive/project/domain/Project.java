package com.csee.swplus.mileage.archive.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "_sw_mileage_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String snum;
    private String name;
    private String role;
    private String description;
    private String content;
    private String achievement;

    @Column(name = "github_link")
    private String githubLink;

    @Column(name = "blog_link")
    private String blogLink;

    @Column(name = "deployed_link")
    private String deployedLink;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String thumbnail;

    @Column(name = "tech_stack")
    private String techStack;
}
