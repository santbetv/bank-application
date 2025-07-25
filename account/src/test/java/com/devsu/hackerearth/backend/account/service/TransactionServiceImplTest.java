package com.devsu.hackerearth.backend.account.service;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceImplTest {

    @MockBean
    private TransactionRepository txRepo;

    @Autowired
    private TransactionService transactionService;

    private Account account;
    private Transaction tx1, tx2;

    @BeforeEach
    void setUp() {
        // Cuenta de prueba
        account = new Account();
        account.setId(42L);
        account.setNumber("ACC-TEST");
        account.setType("SAVINGS");
        account.setInitialAmount(new BigDecimal("1000"));
        account.setIsActive(true);

        // Transacción 1
        tx1 = new Transaction();
        tx1.setId(1L);
        tx1.setDate(LocalDate.of(2025, 7, 25));
        tx1.setType("DEPOSIT");
        tx1.setAmount(new BigDecimal("500.0"));
        tx1.setBalance(new BigDecimal("1500"));
        tx1.setObjAccountTransactions(account);

        // Transacción 2
        tx2 = new Transaction();
        tx2.setId(2L);
        tx2.setDate(LocalDate.of(2025, 7, 26));
        tx2.setType("WITHDRAWAL");
        tx2.setAmount(new BigDecimal("-200.0"));
        tx2.setBalance(new BigDecimal("1300"));
        tx2.setObjAccountTransactions(account);
    }

    @Test
    void getAllReturnsMappedDtos() {
        // dado
        when(txRepo.findAll()).thenReturn(List.of(tx1, tx2));

        // cuando
        List<TransactionDto> result = transactionService.getAll();

        // entonces
        assertThat(result).hasSize(2);

        TransactionDto dto1 = result.get(0);
        assertThat(dto1.getId()).isEqualTo(1L);
        assertThat(dto1.getDate().toString()).isEqualTo("2025-07-25");
        assertThat(dto1.getType()).isEqualTo("DEPOSIT");
        assertThat(dto1.getAmount()).isEqualByComparingTo(new BigDecimal("500.0"));
        assertThat(dto1.getBalance()).isEqualByComparingTo(new BigDecimal("1500"));

        TransactionDto dto2 = result.get(1);
        assertThat(dto2.getId()).isEqualTo(2L);
        assertThat(dto2.getType()).isEqualTo("WITHDRAWAL");
        assertThat(dto2.getAmount()).isEqualByComparingTo(new BigDecimal("-200.0"));
        assertThat(dto2.getBalance()).isEqualByComparingTo(new BigDecimal("1300"));
    }


    @Test
    void getByIdExistingIdReturnsDto() throws BussinesRuleException {
        // dado
        when(txRepo.findById(1L)).thenReturn(Optional.of(tx1));

        // cuando
        TransactionDto dto = transactionService.getById(1L);

        // entonces
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
    }
}