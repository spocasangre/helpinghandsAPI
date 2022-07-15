package com.taquitosncapas.helpinghands.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="organizations")
@Getter
@Setter
public class Organization extends User {
	private String name_org;
	private String register_number;
	private String purpose;
	private String address;
	private String website;
}
