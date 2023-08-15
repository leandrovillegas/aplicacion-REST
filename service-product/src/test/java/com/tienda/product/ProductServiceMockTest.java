package com.tienda.product;

import com.tienda.product.entity.Category;
import com.tienda.product.entity.Product;
import com.tienda.product.repository.ProductRepository;
import com.tienda.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
       // productService = new ProductServiceImpl(productRepository);
        Product product02= Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("629.35"))
                .status("Created")
                .createAt(new Date()).build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product02));
        Mockito.when(productRepository.save(product02)).thenReturn(product02);
    }
@Test
    public void whenValidGetID_ThenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L,Double.parseDouble("1"));

        Assertions.assertThat(newStock.getStock()).isEqualTo(11);


    }
}
