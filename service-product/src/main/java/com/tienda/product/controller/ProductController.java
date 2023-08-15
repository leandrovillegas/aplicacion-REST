package com.tienda.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.product.entity.Category;
import com.tienda.product.entity.Product;
import com.tienda.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products = productService.listAllProduct();
        if(categoryId == null){
                products = productService.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
                products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }


        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId){
        if (productId == null){
            return ResponseEntity.notFound().build();
        }
        Product product= productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping
        public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product){
        product.setId(productId);
        Product updateProduct = productService.updateProduct(product);
        if( updateProduct == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(updateProduct);
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Long productId){
        Product deletedProduct = productService.deleteProduct(productId);
        if (deletedProduct ==  null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(deletedProduct);
    }

    @PutMapping(value = "/{productId}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("productId") Long productId, @RequestParam(name = "quantity", required = true) Double quantity, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Product updatedStockProduct= productService.updateStock(productId,quantity);
        if (updatedStockProduct ==  null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStockProduct);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
