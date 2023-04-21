package com.awbd.lab5.controllers;

import com.awbd.lab5.domain.Product;
import com.awbd.lab5.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    Model model;

    @Mock
    ProductService productService;

    ProductController productController;

    @Captor
    ArgumentCaptor<Product> argumentCaptor;

    @BeforeEach
    public void setUp() throws Exception {
        productController = new ProductController(productService);
    }

    @Test
    public void showById() {
        Long id = 1l;
        Product productTest = new Product();
        productTest.setId(id);

        when(productService.findById(id)).thenReturn(productTest);

        String viewName = productController.getById(id.toString(), model);
        assertEquals("productDetails", viewName);
        verify(productService, times(1)).findById(id);

        verify(model, times(1))
                .addAttribute(eq("product"), argumentCaptor.capture() );

        Product productArg = argumentCaptor.getValue();
        assertEquals(productArg.getId(), productTest.getId() );

    }

}
