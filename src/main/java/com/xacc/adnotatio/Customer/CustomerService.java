package com.xacc.adnotatio.Customer;

import com.xacc.adnotatio.Cart.Cart;
import com.xacc.adnotatio.Cart.CartRepository;
import com.xacc.adnotatio.Customer.dto.CustomerCartOrderDto;
import com.xacc.adnotatio.Customer.dto.CustomerPageRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRequestDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
import com.xacc.adnotatio.Order.Order;
import com.xacc.adnotatio.Order.OrderRepository;
import com.xacc.adnotatio.OrderDetail.OrderDetail;
import com.xacc.adnotatio.Product.Product;
import com.xacc.adnotatio.Product.ProductRepository;
import com.xacc.adnotatio.common.exception.DuplicateResourceException;
import com.xacc.adnotatio.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerMapper customerMapper;

    public Page<CustomerPageRespondDto> getAllCustomers(
            String fullName, Pageable pageable
    ) {
        Page<Customer> customers = customerRepository.findAllPagination(
                fullName, pageable
        );

        return customers.map(customerMapper::toCustomerPageRespond);
    }

    public CustomerRespondDto getCustomerById(Integer id) {
        Customer customer = findCustomerById(id);

        return customerMapper.toCustomerRespond(customer);
    }

    @Transactional
    public CustomerRespondDto createCustomer(CustomerRequestDto requestDto) {
        Customer customer = customerMapper.toEntity(requestDto);

        try {
            Customer savedCustomer = customerRepository.saveAndFlush(customer);

            return customerMapper.toCustomerRespond(savedCustomer);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Customer tidak dapat dibuat karena melanggar constraint database"
            );
        }
    }

    @Transactional
    public CustomerRespondDto updateCustomer(
            Integer id,
            CustomerRequestDto requestDto
    ) {
        Customer customer = findCustomerById(id);

        customerMapper.updateEntity(requestDto, customer);

        try {
            Customer updatedCustomer = customerRepository.saveAndFlush(customer);

            return customerMapper.toCustomerRespond(updatedCustomer);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Customer tidak dapat diperbarui karena melanggar constraint database"
            );
        }
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        Customer customer = findCustomerById(id);
        customer.setDeleted(true);

        customerRepository.saveAndFlush(customer);
    }

    @Transactional
    public void createOrderCart(Integer id) {
        Customer customer = findCustomerById(id);
        List<Cart> cartItems = findCartByCustomerId(id);

        // 2. Buat Order & pasang OrderDetail
        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setCustomer(customer);

        for (Cart cartItem : cartItems) {
            OrderDetail orderDetail = createOrderDetailFromCart(cartItem);

            newOrder.addOrderDetail(orderDetail); // otomatis set order ke orderDetail
        }

        newOrder.setTotalAmount(newOrder.getTotalAmount());

        try {
            // 3. Simpan Order (karena cascade = ALL, orderDetail otomatis ikut tersimpan)
            orderRepository.saveAndFlush(newOrder);

            // 4. Hapus Cart cukup dengan 1 query DELETE efisien
            cartRepository.deleteByCustomerId(Long.valueOf(id));
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(
                    "Order tidak dapat dibuat karena melanggar constraint database"
            );
        }
    }

    private OrderDetail createOrderDetailFromCart(Cart cartItem) {
        OrderDetail detail = new OrderDetail();
        detail.setProduct(cartItem.getProduct());
        detail.setPrice(cartItem.getProduct().getPrice());
        detail.setQuantity(cartItem.getQuantity());
        return detail;
    }

    private Customer findCustomerById(Integer id) {
        return customerRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer ID " + id + " not found"
                ));
    }

    private List<Cart> findCartByCustomerId(Integer id) {
        return cartRepository.findByCustomerId(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart for Customer ID " + id + " not found"
                ));
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product ID " + id + " not found"
                ));
    }
}
