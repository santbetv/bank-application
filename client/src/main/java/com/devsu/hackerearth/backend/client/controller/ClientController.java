package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import com.devsu.hackerearth.backend.client.exception.BussinesRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping()
	public ResponseEntity<List<ClientDto>> getAll(){
		return ResponseEntity.ok(clientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> getById(@PathVariable Long id) throws BussinesRuleException {
		return ResponseEntity.ok(clientService.getById(id));
	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto dto) {
		ClientDto created = clientService.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @Valid @RequestBody ClientDto dto) throws BussinesRuleException {
		return ResponseEntity.ok(clientService.update(id, dto));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @Valid @RequestBody PartialClientDto partial) throws BussinesRuleException {
		return ResponseEntity.ok(clientService.partialUpdate(id, partial));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws BussinesRuleException {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
