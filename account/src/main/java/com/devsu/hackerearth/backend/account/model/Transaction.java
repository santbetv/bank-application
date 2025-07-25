package com.devsu.hackerearth.backend.account.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction extends Base {

    private LocalDate date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;

    //	@Column(name = "account_id")
//	private Long accountId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account objAccountTransactions;
}
