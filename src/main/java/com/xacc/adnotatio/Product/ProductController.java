package com.xacc.adnotatio.Product;

import com.xacc.adnotatio.Product.dto.ProductRequestDto;
import com.xacc.adnotatio.Product.dto.ProductRespondDto;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping("/tes")
    public List<String> findAllProductTest(){
        return productService.tesFindAll();
    }

    @GetMapping
    public ResponseEntity<Page<ProductRespondDto>> getAllProducts(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page minimal 0")
            int page,

            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Size minimal 1")
            @Max(value = 100, message = "Size maksimal 100")
            int size,

            @RequestParam(required = false, defaultValue = "name")
            String sort,

            @RequestParam(required = false, defaultValue = "asc")
            String direction,

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            @RequestParam(required = false)
            Integer minStock,

            @RequestParam(required = false)
            Integer maxStock,

            @RequestParam(required = false)
            Boolean deactivated
            ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sort)
        );

        Page<ProductRespondDto> products =
                productService.getAllProducts(name, minPrice, maxPrice,
                        minStock, maxStock, deactivated, pageable);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRespondDto> getProductById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductRespondDto> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductRespondDto responseDto =
                productService.createProduct(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRespondDto> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                productService.updateProduct(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id
    ) {
        productService.deleteProduct(id);
        //new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }
}
