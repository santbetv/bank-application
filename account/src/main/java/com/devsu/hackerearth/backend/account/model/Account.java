package com.devsu.hackerearth.backend.account.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account extends Base {
    private String number;
    private String type;
    @Column(name = "initial_amount")
    private BigDecimal initialAmount;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "client_id")
    private Long clientId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "objAccountTransactions", cascade = CascadeType.ALL)
    private List<Transaction> accountTransactions;
}
