package com.taquitosncapas.helpinghands.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="masters")
@Getter
@Setter
public class Master extends User {

}
