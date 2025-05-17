package com.csee.swplus.mileage.profile.not_shared.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "_sw_mileage_profile_info")
@Getter
@Setter
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "profile_image_url", length = 50)
    private String profileImageUrl;

    @Column(name = "self_description", length = 50)
    private String selfDescription;

    @Column(length = 30)
    private String job;

    @Column(name = "github_link", length = 40)
    private String githubLink;

    @Column(name = "instagram_link", length = 40)
    private String instagramLink;

    @Column(name = "blog_link", length = 40)
    private String blogLink;

    @Column(name = "linkedin_link",length = 40)
    private String linkedinLink;

    private String snum;

    public Info(String snum, String profileImageUrl, String selfDescription, String job, String githubLink, String instagramLink, String linkedinLink, String blogLink) {
        this.snum = snum;
        this.profileImageUrl = profileImageUrl;
        this.selfDescription = selfDescription;
        this.job = job;
        this.githubLink = githubLink;
        this.instagramLink = instagramLink;
        this.linkedinLink = linkedinLink;
        this.blogLink = blogLink;
    }
}
