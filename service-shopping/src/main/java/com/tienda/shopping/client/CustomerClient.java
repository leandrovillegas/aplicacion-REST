package com.tienda.shopping.client;

import com.tienda.shopping.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "service-customer" ,path = "/customer")
public interface CustomerClient {

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long id);
}
