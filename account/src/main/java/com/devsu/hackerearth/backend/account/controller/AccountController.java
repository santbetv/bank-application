package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getById(@PathVariable Long id) throws BussinesRuleException {
		return ResponseEntity.ok(accountService.getById(id));
	}

	@PostMapping
	public ResponseEntity<AccountDto> create(
			@Valid @RequestBody AccountDto dto) throws BussinesRuleException {
		AccountDto created = accountService.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(
			@PathVariable Long id,
			@Valid @RequestBody AccountDto dto) throws BussinesRuleException {
		return ResponseEntity.ok(accountService.update(id, dto));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(
			@PathVariable Long id,
			@Valid @RequestBody PartialAccountDto partial) throws BussinesRuleException {
		return ResponseEntity.ok(accountService.partialUpdate(id, partial));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws BussinesRuleException {
		accountService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

