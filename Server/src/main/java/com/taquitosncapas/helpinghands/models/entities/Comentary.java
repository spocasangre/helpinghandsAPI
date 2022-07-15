package com.taquitosncapas.helpinghands.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name="comentaries")
@Getter
@Setter
public class Comentary implements Serializable {

    private static final long serialVersionUID = 835137982139701421L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="content", length=100, nullable=false)
    private String content;

    @Column(name="create_at", length=100, nullable=false)
    private Timestamp create_at;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_project",referencedColumnName ="id")
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_user",referencedColumnName ="id")
    @JsonIgnore
    private User user;
}
