package com.xacc.adnotatio.Cart;

import com.xacc.adnotatio.Cart.dto.CartPatchRequestDto;
import com.xacc.adnotatio.Cart.dto.CartRequestDto;
import com.xacc.adnotatio.Cart.dto.CartRespondDto;
import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Cart.dto.CartCustomerRespondDto;
import com.xacc.adnotatio.Customer.CustomerRepository;
import com.xacc.adnotatio.Customer.dto.CustomerRequestDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
import com.xacc.adnotatio.Product.Product;
import com.xacc.adnotatio.Product.ProductRepository;
import com.xacc.adnotatio.common.exception.DuplicateResourceException;
import com.xacc.adnotatio.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public Page<CartCustomerRespondDto> getCustomerCarts(
            String name, Integer id, Pageable pageable
    ) {
        Page<Cart> carts = cartRepository.findAllPagination(
                name, id, pageable
        );

        return carts.map(cartMapper::toCustomerCartsRespond);
    }

    @Transactional
    public CartRespondDto createCart(Integer customerId, CartRequestDto requestDto) {
        Customer customer = findCustomerById(customerId);
        Product product = findProductById(requestDto.productId());

        Cart cart = cartMapper.toEntity(customer, product, requestDto);

        try {
            Cart savedCart = cartRepository.saveAndFlush(cart);

            return cartMapper.toRespond(savedCart);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Item cart gagal ditambahkan karena melanggar constraint database"
            );
        }
    }

    @Transactional
    public void deleteCart(Integer customerId, Integer productId) {
        findCustomerById(customerId);
        findProductById(productId);
        Cart cart = findCartById(customerId,productId);

        cartRepository.delete(cart);
    }

    @Transactional
    public CartRespondDto updateCart(
            Integer customerId,
            Integer productId,
            CartPatchRequestDto requestDto
    ) {
        findCustomerById(customerId);
        findProductById(productId);
        Cart cart = findCartById(customerId,productId);

        cartMapper.updateEntity(requestDto, cart);

        try {
            Cart updatedCart = cartRepository.saveAndFlush(cart);

            return cartMapper.toRespond(updatedCart);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Cart tidak dapat diperbarui karena melanggar constraint database"
            );
        }
    }

    private Customer findCustomerById(Integer id) {
        return customerRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer ID " + id + " not found"
                ));
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product ID " + id + "  not found"
                ));
    }

    private Cart findCartById(Integer customerId, Integer productId) {
        return cartRepository.findCartById(customerId,productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found"
                ));
    }

}
