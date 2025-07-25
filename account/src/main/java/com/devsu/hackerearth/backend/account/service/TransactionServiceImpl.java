package com.devsu.hackerearth.backend.account.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.account.mapper.TransactionResponseMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

import javax.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;
    private final TransactionResponseMapper transactionResponseMapper;

    public TransactionServiceImpl(TransactionRepository txRepo,
                                  AccountRepository accountRepo,
                                  TransactionResponseMapper transactionResponseMapper) {
        this.txRepo = txRepo;
        this.accountRepo = accountRepo;
        this.transactionResponseMapper = transactionResponseMapper;
    }

    @Override
    public List<TransactionDto> getAll() {
        return txRepo.findAll()
                .stream()
                .map(transactionResponseMapper::toDto)
                .toList();
    }

    @Override
    public TransactionDto getById(Long id) throws BussinesRuleException {
        Transaction tx = txRepo.findById(id)
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "Transacción con id " + id + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));
        return transactionResponseMapper.toDto(tx);
    }

    @Override
    @Transactional
    public TransactionDto create(TransactionDto dto) throws BussinesRuleException {
        Account acc = accountRepo.findById(dto.getAccountId())
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "Cuenta con id " + dto.getAccountId() + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));

        //último saldo
        BigDecimal lastBalance = txRepo
                .findTopByObjAccountTransactions_IdOrderByDateDescIdDesc(acc.getId())
                .map(Transaction::getBalance)
                .orElse(acc.getInitialAmount());

        //nuevo saldo
        BigDecimal newBalance = lastBalance.add(dto.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BussinesRuleException(
                    1L,
                    "001",
                    "Saldo no disponible",
                    HttpStatus.BAD_REQUEST
            );
        }

        Transaction tx = transactionResponseMapper.toEntity(dto);
        tx.setObjAccountTransactions(acc);
        tx.setBalance(newBalance);
        tx = txRepo.save(tx);

        return transactionResponseMapper.toDto(tx);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {

        LocalDate start = dateTransactionStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dateTransactionEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Object[]> rows = txRepo.findReportByClientAndDateBetween(clientId, start, end);
        return transactionResponseMapper.toBankStatementDto(rows);
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) throws BussinesRuleException {
        return txRepo.findTopByObjAccountTransactions_IdOrderByDateDescIdDesc(accountId)
                .map(transactionResponseMapper::toDto)
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "No hay transacciones para la cuenta " + accountId,
                        HttpStatus.NOT_FOUND
                ));
    }
}
