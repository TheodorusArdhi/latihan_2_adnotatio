package com.xacc.adnotatio.Cart;

import com.xacc.adnotatio.Cart.dto.CartCustomerRespondDto;
import com.xacc.adnotatio.Cart.dto.CartPatchRequestDto;
import com.xacc.adnotatio.Cart.dto.CartRequestDto;
import com.xacc.adnotatio.Cart.dto.CartRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers/{id}/cart")
@RequiredArgsConstructor
@Validated
public class CartController {
    private final CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<Page<CartCustomerRespondDto>> getCutomerCarts(
            @PathVariable Integer id,

            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page minimal 0")
            int page,

            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Size minimal 1")
            @Max(value = 100, message = "Size maksimal 100")
            int size,

            @RequestParam(required = false, defaultValue = "name")
            String sort,

            @RequestParam(required = false)
            String name
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size
                ,Sort.by(Sort.Direction.ASC, "product." + sort)
        );

        Page<CartCustomerRespondDto> customerCarts =
                cartService.getCustomerCarts(name, id, pageable);

        return ResponseEntity.ok(customerCarts);
    }

    @PostMapping("/items")
    public ResponseEntity<CartRespondDto> createCart(
            @PathVariable Integer id,
            @Valid @RequestBody CartRequestDto requestDto
    ) {
        CartRespondDto respondDto =
                cartService.createCart(id, requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respondDto);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable("id") Integer customerId, @PathVariable Integer productId
    ) {
        cartService.deleteCart(customerId, productId);
        //new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/items/{productId}")
    public ResponseEntity<CartRespondDto> updateCart(
            @PathVariable("id") Integer customerId, @PathVariable Integer productId,
            @Valid @RequestBody CartPatchRequestDto requestDto
    ){
        return ResponseEntity.ok(
                cartService.updateCart(customerId,productId,requestDto)
        );
    }
}
