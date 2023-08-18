package com.tienda.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.customer.entity.Customer;
import com.tienda.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customerList = customerService.listAllCustomers();
        if(customerList == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customerList);
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long id){
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customer);

    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
       Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);

    }

    @PutMapping(value = "/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "customerId") Long id, @RequestBody Customer customer ){
        Customer existingCustomer= customerService.getCustomer(id);
        if(existingCustomer == null){
            return  ResponseEntity.notFound().build();
        }
        customer.setId(id);
        customerService.updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(existingCustomer);
    }

    @DeleteMapping(value = "/delete/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "customerId") Long id){
        Customer deletedCustomer=  customerService.deleteCustomer(id);
        if(deletedCustomer == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }



    /*------------------Format Message of Errors-----------------------*/
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
