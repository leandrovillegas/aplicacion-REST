package com.tienda.shopping.client;

import com.tienda.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-product" , path = "/products")
public interface ProductClient {

    @GetMapping(value = "/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId);

    @PutMapping(value = "/{productId}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("productId") Long productId, @RequestParam(name = "quantity", required = true) Double quantity);
}
