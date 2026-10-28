package com.xacc.adnotatio.Product;

import com.xacc.adnotatio.Product.dto.ProductRequestDto;
import com.xacc.adnotatio.Product.dto.ProductRespondDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductRespondDto toProductRespond(Product product);
    Product toEntity(ProductRequestDto productRequestDto);
    void updateEntity(
            ProductRequestDto requestDto,
            @MappingTarget Product product
    );
}
