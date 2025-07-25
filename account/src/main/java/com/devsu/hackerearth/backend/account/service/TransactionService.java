package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import org.springframework.data.repository.query.Param;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public interface TransactionService {

    List<TransactionDto> getAll();

    TransactionDto getById(Long id) throws BussinesRuleException;

    TransactionDto create(TransactionDto transactionDto) throws BussinesRuleException;

    List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, @Param("dateTransactionStart") Date dateTransactionStart, @Param("dateTransactionEnd") Date dateTransactionEnd) throws BussinesRuleException;

    TransactionDto getLastByAccountId(Long accountId) throws BussinesRuleException;
}
