package com.hello.tdd.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.tdd.spring.api.controller.order.OrderController;
import com.hello.tdd.spring.api.controller.product.ProductController;
import com.hello.tdd.spring.api.service.order.OrderService;
import com.hello.tdd.spring.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {OrderController.class, ProductController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;

    @MockBean
    private OrderService orderService;
}
