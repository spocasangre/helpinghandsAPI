package com.taquitosncapas.helpinghands.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taquitosncapas.helpinghands.models.dtos.project.CreateProjectRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="projects")
@Getter
@Setter
public class Project implements Serializable{

    private static final long serialVersionUID = 835137982139701421L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description", length = 180)
    private String description;

    @Column(name = "place", length = 180)
    private String place;

    @Column(name = "date")
    private Date date;

    @Column(name = "duration", length = 180)
    private String duration;

    @Column(name = "active")
    private Integer active = 0;

    @Column(name = "isPending")
    private Integer isPending = 1;

    @Column(name = "isFinished")
    private Integer isFinished = 0;

    @Column(name = "response_date")
    private Date response_date;

    @Column(name = "create_at",updatable = false)
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_owner")
    @JsonIgnore
    private User owner;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comentary> comentaries;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Application> applications;

    public Project(CreateProjectRequest createProjectRequest,User owner, Category category) {
        this.title = createProjectRequest.getTitle();
        this.description = createProjectRequest.getDescription();
        this.place = createProjectRequest.getPlace();
        this.date = createProjectRequest.getDate();
        this.duration = createProjectRequest.getDuration();
        this.active = createProjectRequest.getActive();
        this.isPending = createProjectRequest.getIsPending();
        this.isFinished = createProjectRequest.getIsFinished();
        this.response_date = createProjectRequest.getResponse_date();
        this.owner = owner;
        this.category = category;
    }

    public Project() {
        super();
    }


}
