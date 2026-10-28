package com.xacc.adnotatio.Region;

import com.xacc.adnotatio.Delivery.Delivery;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regions")
@Data
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    // Relasi ManyToMany dengan Delivery (Sisi Pemilik Aktif)
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "region_deliveries",
            joinColumns = @JoinColumn(name = "region_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_id")
    )
    private List<Delivery> deliveries = new ArrayList<>();
}
