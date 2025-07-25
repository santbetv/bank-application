package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import com.devsu.hackerearth.backend.account.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;

public interface AccountService {

	List<AccountDto> getAll();
	AccountDto getById(Long id) throws BussinesRuleException;
	AccountDto create(AccountDto dto) throws BussinesRuleException;
	AccountDto update(Long id, AccountDto dto) throws BussinesRuleException;
	AccountDto partialUpdate(Long id, PartialAccountDto partial) throws BussinesRuleException;
	void deleteById(Long id) throws BussinesRuleException;
}
