package com.devsu.hackerearth.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Validated
public class AccountDto {

	private Long id;

	@NotEmpty(message = "El número de cuenta es obligatorio")
	private String number;

	@NotEmpty(message = "El tipo de cuenta es obligatorio")
	private String type;

	private String initialAmount;

	@NotNull(message = "El estado isActive es obligatorio")
	private Boolean isActive;

	@NotNull(message = "El clientId es obligatorio")
	private Long clientId;
}
