package com.hello.tdd.spring.api.service.product.request;

import com.hello.tdd.spring.domain.product.Product;
import com.hello.tdd.spring.domain.product.ProductSellingStatus;
import com.hello.tdd.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(this.type)
                .name(this.name)
                .sellingStatus(this.sellingStatus)
                .price(this.price)
                .build();
    }
}
