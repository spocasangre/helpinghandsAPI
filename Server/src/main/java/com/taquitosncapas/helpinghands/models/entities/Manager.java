package com.taquitosncapas.helpinghands.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="managers")
@Getter
@Setter
public class Manager extends User {

}
