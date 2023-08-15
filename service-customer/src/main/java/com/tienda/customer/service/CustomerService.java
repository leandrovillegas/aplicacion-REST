package com.tienda.customer.service;

import com.tienda.customer.entity.Customer;
import com.tienda.customer.entity.Region;

import java.util.List;

public interface CustomerService {


    public List<Customer> listAllCustomers();
    public Customer createCustomer (Customer customer);
    public Customer getCustomer(Long id);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Long id);
    public List<Customer> findCustomerByRegion(Region region);

}
