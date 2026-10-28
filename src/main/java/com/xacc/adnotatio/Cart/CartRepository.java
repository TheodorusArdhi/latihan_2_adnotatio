package com.xacc.adnotatio.Cart;

import com.xacc.adnotatio.Customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,CartId> {
    @Query("""
            SELECT car
            FROM Cart car
            JOIN car.product p
            JOIN car.customer cus
            WHERE cus.id = :id
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
            """)
    Page<Cart> findAllPagination(
            @Param("name") String name,
            Integer id,
            Pageable pageable
    );

    @Query("""
            SELECT car
            FROM Cart car
            JOIN car.product p
            JOIN car.customer cus
            WHERE cus.id = :customerId AND p.id = :productId
            """)
    Optional<Cart> findCartById(Integer customerId, Integer productId);

    Optional<List<Cart>> findByCustomerId(Long customerId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
