package com.devsu.hackerearth.backend.account.mapper;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    Transaction toEntity(TransactionDto dto);

    @InheritInverseConfiguration
    TransactionDto toDto(Transaction entity);

    private BankStatementDto toBankStatement(Object[] row) {
        return BankStatementDto.builder()
                .date((LocalDate) row[0])
                .client((String) row[1])
                .accountNumber((String) row[2])
                .accountType((String) row[3])
                .initialAmount(BigDecimal.valueOf(((BigDecimal) row[4]).doubleValue()))
                .isActive((Boolean) row[5])
                .transactionType((String) row[6])
                .amount(BigDecimal.valueOf(((BigDecimal) row[7]).doubleValue()))
                .balance(BigDecimal.valueOf(((BigDecimal) row[8]).doubleValue()))
                .build();
    }

    default List<BankStatementDto> toBankStatementDto(List<Object[]> rows) {
        return rows.stream()
                .map(this::toBankStatement)
                .toList();
    }
}
