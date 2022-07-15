package com.taquitosncapas.helpinghands.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 835137982139701421L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_role")
	private Long id_role;

	@Column(length = 50)
	private String name;

	@Column(length = 50)
	private String lastname;

	private String gender;

	@Column(length=50)
	private String telephone_number;

	private String birth_date;

	@Column(name = "email", length = 50, unique = true)
	private String email;

	@Column(name = "password")
	private String pass;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_role",insertable = false, updatable = false)
	private Role role;

	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Token> tokens;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Project> projects;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Application> applications;

}
