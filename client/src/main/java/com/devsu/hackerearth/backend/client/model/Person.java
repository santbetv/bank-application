package com.devsu.hackerearth.backend.client.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Person extends Base{
	private String name;
	private String dni;
	private String gender;
	private int age;
	private String address;
	private String phone;
}
