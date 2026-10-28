package com.xacc.adnotatio.Order;

import com.xacc.adnotatio.Customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            SELECT o
            FROM Order o
            JOIN o.customer c
            WHERE (:fromDate IS NULL OR o.orderDate >= :fromDate)
            AND (:toDate IS NULL OR o.orderDate <= :toDate)
            """)
    Page<Order> findAllPagination(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );
}
