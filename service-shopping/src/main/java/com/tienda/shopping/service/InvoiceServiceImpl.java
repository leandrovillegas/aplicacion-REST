package com.tienda.shopping.service;

import com.tienda.shopping.client.CustomerClient;
import com.tienda.shopping.client.ProductClient;
import com.tienda.shopping.entity.Invoice;
import com.tienda.shopping.entity.InvoiceItem;
import com.tienda.shopping.model.Customer;
import com.tienda.shopping.model.Product;
import com.tienda.shopping.repository.InvoiceItemsRepository;
import com.tienda.shopping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.InvocationEvent;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        //Made a change to return with productos from the service-products
         List<Invoice> invoices = invoiceRepository.findAll();
        invoices.forEach(invoice -> {
            this.getInvoice(invoice.getId());
        });
        return invoices;
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice());
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB= invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach(invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });
        return invoiceDB ;
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice != null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> listItems = invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(listItems);
        }

        return invoice;
    }
}
