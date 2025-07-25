package com.devsu.hackerearth.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Para obtener el último movimiento de una cuenta
    Optional<Transaction> findTopByObjAccountTransactions_IdOrderByDateDescIdDesc(Long accountId);


    @Query("""
            SELECT t.date,
                   a.number,
                   a.number,
                   a.type,
                   a.initialAmount,
                   a.isActive,
                   t.type,
                   t.amount,
                   t.balance
            FROM Transaction t
            JOIN t.objAccountTransactions a
            WHERE a.clientId = :clientId
              AND t.date BETWEEN :dateStart AND :dateEnd
            ORDER BY t.date
            """)
    List<Object[]> findReportByClientAndDateBetween(
            @Param("clientId") Long clientId,
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd
    );
}
