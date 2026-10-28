package com.xacc.adnotatio.Product;

import com.xacc.adnotatio.Product.dto.ProductRequestDto;
import com.xacc.adnotatio.Product.dto.ProductRespondDto;
import com.xacc.adnotatio.common.exception.DuplicateResourceException;
import com.xacc.adnotatio.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<String> tesFindAll(){
        return List.of(productRepository.findAll().toString());
    }

    public Page<ProductRespondDto> getAllProducts(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            Integer maxStock,
            Boolean deactivated,
            Pageable pageable
    ) {
        Page<Product> products = productRepository.findAllPagination(
                name, minPrice, maxPrice,
                minStock, maxStock, deactivated,
                pageable
        );

        return products.map(productMapper::toProductRespond);
    }

    public ProductRespondDto getProductById(Integer id) {
        Product product = findProductById(id);

        return productMapper.toProductRespond(product);
    }

    @Transactional
    public ProductRespondDto createProduct(ProductRequestDto requestDto) {
//        if (productRepository.existsByName(requestDto.name())) {
//            throw new DuplicateResourceException(
//                    "Product dengan nama tersebut sudah tersedia"
//            );
//        }

        Product product = productMapper.toEntity(requestDto);

        try {
            Product savedProduct = productRepository.saveAndFlush(product);

            return productMapper.toProductRespond(savedProduct);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Product tidak dapat dibuat karena melanggar constraint database"
            );
        }
    }

    @Transactional
    public ProductRespondDto updateProduct(
            Integer id,
            ProductRequestDto requestDto
    ) {
        Product product = findProductById(id);

//        if (productRepository.existsByNameAndIdNot(requestDto.name(), id)) {
//            throw new DuplicateResourceException(
//                    "Customer dengan nama tersebut sudah digunakan"
//            );
//        }

        productMapper.updateEntity(requestDto, product);

        try {
            Product updatedProduct = productRepository.saveAndFlush(product);

            return productMapper.toProductRespond(updatedProduct);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Product tidak dapat diperbarui karena melanggar constraint database"
            );
        }
    }

    @Transactional
    public void deleteProduct(Integer id) {
        Product product = findProductById(id);

        productRepository.delete(product);
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product ID " + id + " not found"
                ));
    }
}
