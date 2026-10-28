package com.xacc.adnotatio.Delivery;

import com.xacc.adnotatio.Order.Order;
import com.xacc.adnotatio.Region.Region;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deliveries")
@Data
public class Delivery {

    @Id
    @Column(length = 10)
    private String id; // ID diisi manual (String, contoh: 'EXD', 'NOD')

    @Column(nullable = false, length = 100)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal cost;

    // Sisi pasif dari relasi ManyToMany dengan Region
    @ToString.Exclude
    @ManyToMany(mappedBy = "deliveries")
    private List<Region> regions = new ArrayList<>();

    // Relasi OneToMany: 1 Delivery dapat digunakan di banyak Order
    @ToString.Exclude
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}
