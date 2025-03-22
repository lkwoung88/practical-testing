package com.hello.tdd.spring.api.service.product;

import com.hello.tdd.spring.api.service.product.request.ProductCreateServiceRequest;
import com.hello.tdd.spring.api.service.product.response.ProductResponse;
import com.hello.tdd.spring.domain.product.Product;
import com.hello.tdd.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hello.tdd.spring.domain.product.ProductSellingStatus.forDisplay;

/**
 * readOnly = true : 읽기 전용
 * CUD (X) , only R (O)
 *
 * CQRS : Command, Query 분리 -> DB end point 구분 가능 (read/write)
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 동시성 이슈 / unique key and retry... / UUID
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
