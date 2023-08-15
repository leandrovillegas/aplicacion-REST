package com.tienda.customer.service;

import com.tienda.customer.entity.Customer;
import com.tienda.customer.entity.Region;
import com.tienda.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberId(customer.getNumberId());
        if(customerDB != null){
            return customerDB;
        }
        customer.setState("CREATED");
        return  customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer updateCustomer = getCustomer(customer.getId());
        if(updateCustomer == null){
            return null;
        }
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setNumberId(customer.getNumberId());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setPhotoUrl(customer.getPhotoUrl());
        updateCustomer.setRegion(customer.getRegion());
        updateCustomer.setState("UPDATE");
        return customerRepository.save(updateCustomer);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer deletedCustomer = getCustomer(id);
        if(deletedCustomer == null){
            return null;
        }
        deletedCustomer.setState("DELETED");
        return customerRepository.save(deletedCustomer);
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
      return customerRepository.findByRegion(region);
    }
}
