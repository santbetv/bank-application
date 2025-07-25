package com.devsu.hackerearth.backend.account.service;

import java.math.BigDecimal;
import java.util.List;

import com.devsu.hackerearth.backend.account.client.ClientServiceFeign;
import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.account.mapper.AccountResponseMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repo;
    private final AccountResponseMapper accountResponseMapper;
    private final ClientServiceFeign clientServiceFeign;

    @Autowired
    public AccountServiceImpl(AccountRepository repo, AccountResponseMapper accountResponseMapper, ClientServiceFeign clientServiceFeign) {
        this.repo = repo;
        this.accountResponseMapper = accountResponseMapper;
        this.clientServiceFeign = clientServiceFeign;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountResponseMapper.toListAccountDTO(repo.findAll());
    }

    @Override
    public AccountDto getById(Long id) throws BussinesRuleException {
        Account acc = repo.findById(id)
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "Cuenta con id " + id + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));
        return accountResponseMapper.toAccountDto(acc);
    }

    @Override
    @Transactional
    public AccountDto create(AccountDto dto) throws BussinesRuleException {
        try {
            clientServiceFeign.getById(dto.getClientId());
        } catch (FeignException.NotFound nf) {
            throw new BussinesRuleException(
                    1L,
                    "001",
                    "No existe cliente con id " + dto.getClientId(),
                    HttpStatus.NOT_FOUND
            );
        } catch (FeignException fe) {
            throw new BussinesRuleException(
                    1L,
                    "001",
                    "Error al consultar servicio de clientes: " + fe.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        Account saved = repo.save(accountResponseMapper.toAccountEntity(dto));
        return accountResponseMapper.toAccountDto(saved);
    }

    @Override
    @Transactional
    public AccountDto update(Long id, AccountDto dto) throws BussinesRuleException {
        Account existing = repo.findById(id)
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "Cuenta con id " + id + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));
        existing.setNumber(dto.getNumber());
        existing.setType(dto.getType());
        existing.setInitialAmount(new BigDecimal(dto.getInitialAmount()));
        existing.setIsActive(dto.getIsActive());
        existing.setClientId(dto.getClientId());
        Account saved = repo.save(existing);
        return accountResponseMapper.toAccountDto(saved);
    }

    @Override
    @Transactional
    public AccountDto partialUpdate(Long id, PartialAccountDto partial) throws BussinesRuleException {
        Account existing = repo.findById(id)
                .orElseThrow(() -> new BussinesRuleException(
                        1L,
                        "001",
                        "Cuenta con id " + id + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));
        existing.setIsActive(partial.getIsActive());
        Account saved = repo.save(existing);
        return accountResponseMapper.toAccountDto(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws BussinesRuleException {
        if (!repo.existsById(id)) {
            throw new BussinesRuleException(
                    1L,
                    "001",
                    "Cuenta con id " + id + " no encontrada",
                    HttpStatus.NOT_FOUND
            );
        }
        repo.deleteById(id);
    }
}
