package com.shaik.productCatalog.controller;

import com.shaik.productCatalog.dto.ProductRequest;
import com.shaik.productCatalog.dto.ProductResponse;
import com.shaik.productCatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class HomeController {

    @Autowired
    ProductService productService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        Long productID = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully " + productID);
    }

    @GetMapping("/viewproducts")
    @ResponseStatus(HttpStatus.OK)
    private List<ProductResponse>  listallProducts(){
        return productService.getAllProducts();
    }

}
