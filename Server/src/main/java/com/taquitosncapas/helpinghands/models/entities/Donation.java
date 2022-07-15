package com.taquitosncapas.helpinghands.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name="donations")
@Getter
@Setter
public class Donation implements Serializable {

    private static final long serialVersionUID = 835137982139701421L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="value", nullable=false)
    private Integer value;

    @Column(name = "create_at", updatable = false)
    private Timestamp createAt;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_project",referencedColumnName ="id")
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_user",referencedColumnName ="id")
    @JsonIgnore
    private User user;
}
