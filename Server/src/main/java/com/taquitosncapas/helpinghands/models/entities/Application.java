package com.taquitosncapas.helpinghands.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name="applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application implements Serializable{

    private static final long serialVersionUID = 835137982139701421L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response")
    private Integer response;

    @Column(name = "isAbandoned")
    private Integer isAbandoned;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "responded_at")
    private Timestamp respondedAt;

    @Column(name = "abandonedAt")
    private Timestamp abandonedAt;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_project",referencedColumnName ="id")
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_user",referencedColumnName ="id")
    @JsonIgnore
    private User user;

}
