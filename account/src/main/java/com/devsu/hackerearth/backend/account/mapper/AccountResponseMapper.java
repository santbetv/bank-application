package com.devsu.hackerearth.backend.account.mapper;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountResponseMapper {

    Account toAccountEntity(AccountDto dto);

    @InheritInverseConfiguration
    List<AccountDto> toListAccountDTO(List<Account> entities);

    @InheritInverseConfiguration
    AccountDto toAccountDto(Account entity);
}
