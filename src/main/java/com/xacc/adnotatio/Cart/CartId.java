package com.xacc.adnotatio.Cart;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
public class CartId implements Serializable {
    private Long customerId;
    private Integer productId;
}
