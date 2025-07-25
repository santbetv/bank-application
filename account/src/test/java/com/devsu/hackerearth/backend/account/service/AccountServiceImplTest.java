package com.devsu.hackerearth.backend.account.service;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.account.mapper.AccountResponseMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountResponseMapper accountResponseMapper;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(10L);
        account.setNumber("ACC-123");
        account.setType("CHECKING");
        account.setInitialAmount(new BigDecimal("250.50"));
        account.setIsActive(false);
        account.setClientId(7L);
    }

    @Test
    void getAllReturnsMappedDtos() {
        // dado
        when(accountRepo.findAll()).thenReturn(List.of(account));

        // cuando
        List<AccountDto> dtos = accountService.getAll();

        // entonces
        assertThat(dtos).hasSize(1);
        AccountDto dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getNumber()).isEqualTo("ACC-123");
        assertThat(dto.getType()).isEqualTo("CHECKING");
        assertThat(dto.getInitialAmount()).isEqualTo("250.50");
        assertThat(dto.getIsActive()).isFalse();
        assertThat(dto.getClientId()).isEqualTo(7L);
    }

    @Test
    void getByIdExistingIdReturnsDto() throws BussinesRuleException {
        // dado
        when(accountRepo.findById(10L)).thenReturn(Optional.of(account));

        // cuando
        AccountDto dto = accountService.getById(10L);

        // entonces
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getNumber()).isEqualTo("ACC-123");
        assertThat(dto.getIsActive()).isFalse();
        assertThat(dto.getClientId()).isEqualTo(7L);
    }
}