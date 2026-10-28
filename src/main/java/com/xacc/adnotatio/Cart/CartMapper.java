package com.xacc.adnotatio.Cart;

import com.xacc.adnotatio.Cart.dto.*;
import com.xacc.adnotatio.Customer.Customer;
import com.xacc.adnotatio.Product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    CartCustomerRespondDto toCustomerCartsRespond(Cart cart);

    // 1. toEntity: customer & product otomatis ter-mapping karena namanya sama
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addedDate", ignore = true)
    @Mapping(source = "cartRequestDto.quantity", target = "quantity")
    Cart toEntity(Customer customer, Product product, CartRequestDto cartRequestDto);

    @Mapping(source = "customer", target = "cartCustomerDto")
    @Mapping(source = "cart", target = "cartProductDto")
    CartRespondDto toRespond(Cart cart);

    // Otomatis mapping Customer -> CartRespondDto.CartCustomerDto
    CartRespondDto.CartCustomerDto toCustomerDto(Customer customer);

    // Mapping Cart -> CartRespondDto.CartProductDto via constructor record
    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "addedDate", target = "addedDate")
    CartRespondDto.CartProductDto toProductDto(Cart cart);

    void updateEntity(
            CartPatchRequestDto RequestDto,
            @MappingTarget Cart cart
    );
}