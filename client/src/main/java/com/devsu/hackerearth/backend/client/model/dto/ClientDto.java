package com.devsu.hackerearth.backend.client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@Validated
public class ClientDto {

	private Long id;
	@NotEmpty(message = "no puede estar vacio")
	private String dni;
	@NotEmpty(message = "no puede estar vacio")
	private String name;
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 12, message = "el tamaño debe de estar entre 2 y 12")
	@Column(nullable = false)
	private String password;
	@NotEmpty(message = "no puede estar vacio")
	private String gender;
	@NotNull(message = "la edad es obligatoria")
	@Min(value = 1, message = "la edad debe ser >= 1")
	private int age;
	private String address;
	private String phone;
	private boolean isActive;
}
