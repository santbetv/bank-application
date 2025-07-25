package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable Long id) throws BussinesRuleException {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(
            @Valid @RequestBody TransactionDto dto) throws BussinesRuleException {
        TransactionDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/clients/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd
    ) throws BussinesRuleException {
        return ResponseEntity.ok(
                service.getAllByAccountClientIdAndDateBetween(
                        clientId, dateTransactionStart, dateTransactionEnd
                )
        );
    }
}
