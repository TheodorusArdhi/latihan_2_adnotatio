package com.xacc.adnotatio.Customer;

import com.xacc.adnotatio.Cart.Cart;
import com.xacc.adnotatio.Customer.dto.CustomerCartOrderDto;
import com.xacc.adnotatio.Customer.dto.CustomerPageRespondDto;
import com.xacc.adnotatio.Customer.dto.CustomerRequestDto;
import com.xacc.adnotatio.Customer.dto.CustomerRespondDto;
import com.xacc.adnotatio.Order.Order;
import com.xacc.adnotatio.OrderDetail.OrderDetail;
import com.xacc.adnotatio.Product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRespondDto toCustomerRespond(Customer customer);
    CustomerPageRespondDto toCustomerPageRespond(Customer customer);
    Customer toEntity(CustomerRequestDto customerRequestDto);
    void updateEntity(
            CustomerRequestDto customerRequestDto,
            @MappingTarget Customer customer
    );

    CustomerCartOrderDto toOrderDetailRequest(Product product, Cart cart, Order order);
    OrderDetail cartToOrderDetail(CustomerCartOrderDto customerCartOrderDto);
}
