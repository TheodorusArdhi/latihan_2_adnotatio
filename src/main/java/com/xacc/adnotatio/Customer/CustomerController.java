package com.xacc.adnotatio.Customer;

import com.xacc.adnotatio.Cart.CartService;
import com.xacc.adnotatio.Customer.dto.CustomerPageRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRequestDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerService customerService;
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CustomerPageRespondDto>> getAllCutomers(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page minimal 0")
            int page,

            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Size minimal 1")
            @Max(value = 100, message = "Size maksimal 100")
            int size,

            @RequestParam(required = false, defaultValue = "lastName")
            String sort,

            @RequestParam(required = false)
            String fullName
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, sort)
        );

        Page<CustomerPageRespondDto> customers =
                customerService.getAllCustomers(fullName, pageable);

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRespondDto> getCustomerById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    @PostMapping
    public ResponseEntity<CustomerRespondDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto requestDto
    ) {
        CustomerRespondDto responseDto =
                customerService.createCustomer(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRespondDto> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                customerService.updateCustomer(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer id
    ) {
        customerService.deleteCustomer(id);
        //new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Void> createOrderCart(
        @PathVariable Integer id
    ) {
        customerService.createOrderCart(id);
        //new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }
}
