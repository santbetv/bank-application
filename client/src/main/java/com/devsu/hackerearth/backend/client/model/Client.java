package com.devsu.hackerearth.backend.client.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client extends Person {
	private String password;
	@Column(name = "is_active")
	private boolean isActive;
}
