package com.xacc.adnotatio.Order;

import com.xacc.adnotatio.Customer.dto.CustomerPageRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
import com.xacc.adnotatio.Order.dto.OrderProductRespondDto;
import com.xacc.adnotatio.Order.dto.OrderRespondDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderRespondDto>> getAllOrders(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page minimal 0")
            int page,

            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Size minimal 1")
            @Max(value = 100, message = "Size maksimal 100")
            int size,

            @RequestParam(required = false, defaultValue = "id")
            String sort,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, sort)
        );

        Page<OrderRespondDto> orders =
                orderService.getAllOrders(fromDate, toDate, pageable);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProductRespondDto> getOrderById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );
    }
}
