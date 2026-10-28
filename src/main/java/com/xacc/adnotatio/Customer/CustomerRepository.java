package com.xacc.adnotatio.Customer;

import com.xacc.adnotatio.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("""
            SELECT c
            FROM Customer c
            WHERE (:fullName IS NULL OR LOWER(CONCAT(c.firstName,' ',c.lastName)) LIKE LOWER(CONCAT('%', CAST(:fullName AS string), '%')))
            """)
    Page<Customer> findAllPagination(
            @Param("fullName") String fullName,
            Pageable pageable
    );
}
