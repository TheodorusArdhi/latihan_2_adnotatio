package com.xacc.adnotatio.OrderDetail;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class OrderDetailId implements Serializable {
    private Long orderId;
    private Integer productId;
}
