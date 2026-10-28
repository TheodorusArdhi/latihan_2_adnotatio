package com.xacc.adnotatio.Cart;

import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @EmbeddedId
    private CartId id = new CartId();

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate = LocalDateTime.now();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

}