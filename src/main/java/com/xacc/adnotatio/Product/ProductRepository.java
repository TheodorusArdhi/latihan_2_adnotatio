package com.xacc.adnotatio.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product,Integer>{
    @Query("""
            SELECT p
            FROM Product p
            WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
            AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND (:minStock IS NULL OR p.stock >= :minStock)
              AND (:maxStock IS NULL OR p.stock <= :maxStock)
              AND (:deactivated IS NULL OR p.deactivated = :deactivated)
            """)
    Page<Product> findAllPagination(
            @Param("name") String name,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            @Param("maxStock") Integer maxStock,
            @Param("deactivated") Boolean deactivated,
            Pageable pageable
    );

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
