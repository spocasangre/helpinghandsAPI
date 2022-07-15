package com.taquitosncapas.helpinghands.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="roles")
@Getter
@Setter
public class Role implements Serializable {

	private static final long serialVersionUID = 835137982139701421L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String content;

	@OneToMany
	@JsonIgnore
	@JoinColumn(name="id_role")
	private Set<User> users;
}
