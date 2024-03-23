package com.shaik.productCatalog.service;

import com.shaik.productCatalog.dto.ProductRequest;
import com.shaik.productCatalog.dto.ProductResponse;
import com.shaik.productCatalog.model.Product;
import com.shaik.productCatalog.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public Long createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productPrice(productRequest.getProductPrice())
                .productDescription(productRequest.getProductDescription())
                .build();
        productRepository.save(product);
        log.info("product {} is saved in db" , product.getProductId());
        return product.getProductId();
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
       return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse
                .builder()
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .build();
        return productResponse;
    }
}
