package com.xacc.adnotatio.Order;

import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Customer.dto.CustomerPageRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
import com.xacc.adnotatio.Order.dto.OrderProductRespondDto;
import com.xacc.adnotatio.Order.dto.OrderRespondDto;
import com.xacc.adnotatio.OrderDetail.OrderDetail;
import com.xacc.adnotatio.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Page<OrderRespondDto> getAllOrders(
            LocalDate fromDate, LocalDate toDate, Pageable pageable
    ) {
        Page<Order> orders = orderRepository.findAllPagination(
                fromDate, toDate, pageable
        );

        return orders.map(orderMapper::toOrderRespond);
    }

    public OrderProductRespondDto getOrderById(Integer id) {
        Order order = findOrderById(id);

        return orderMapper.toOrderProductRespond(order);
    }


    private Order findOrderById(Integer id) {
        return orderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order ID " + id + " not found"
                ));
    }

//    private List<OrderDetail> findOrderDetailById(Integer id) {
//        return orderRepository.findById(Long.valueOf(id))
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Order ID " + id + " not found"
//                ));
//    }
}
