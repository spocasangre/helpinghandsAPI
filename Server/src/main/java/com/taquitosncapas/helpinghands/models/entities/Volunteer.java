package com.taquitosncapas.helpinghands.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="volunteers")
@Getter
@Setter
public class Volunteer extends User {
	private String college;
	private String career;
}
