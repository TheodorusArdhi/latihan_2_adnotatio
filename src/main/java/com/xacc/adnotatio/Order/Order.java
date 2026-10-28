package com.xacc.adnotatio.Order;

import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Delivery.Delivery;
import com.xacc.adnotatio.OrderDetail.OrderDetail;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "delivery_fee", precision = 12, scale = 2)
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    // Relasi ManyToOne dengan Customer
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Relasi ManyToOne dengan Delivery
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // Relasi OneToMany ke Entitas Perantara OrderDetail
    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
    public BigDecimal getTotalAmount(){
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails) {
            total = total.add(
                    orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));
        }
        return total;
    }

    public String getFullname(){
        return customer.getFirstName() + " " + customer.getLastName();
    }
}