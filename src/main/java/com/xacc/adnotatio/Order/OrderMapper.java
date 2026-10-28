package com.xacc.adnotatio.Order;

import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Order.dto.OrderItemRespondDto;
import com.xacc.adnotatio.Order.dto.OrderProductRespondDto;
import com.xacc.adnotatio.Order.dto.OrderRespondDto;
import com.xacc.adnotatio.OrderDetail.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer", target = "customerName", qualifiedByName = "mapCustomerName")
    OrderRespondDto toOrderRespond(Order order);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer", target = "customerName", qualifiedByName = "mapCustomerName")
    @Mapping(source = "orderDetails", target = "orderItems")
    OrderProductRespondDto toOrderProductRespond(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "price", target = "unitPrice")
    @Mapping(source = ".", target = "subTotal", qualifiedByName = "calculateSubtotal")
    OrderItemRespondDto toOrderItemRespondDto(OrderDetail orderDetail);

    @Named("mapCustomerName")
    default String mapCustomerName(Customer customer) {
        if (customer == null) {
            return null;
        }
        String firstName = customer.getFirstName() != null ? customer.getFirstName() : "";
        String lastName = customer.getLastName() != null ? customer.getLastName() : "";

        return (firstName + " " + lastName).trim();
    }

    @Named("calculateSubtotal")
    default BigDecimal calculateSubtotal(OrderDetail orderDetail) {
        if (orderDetail == null || orderDetail.getPrice() == null || orderDetail.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        return orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
    }
}
