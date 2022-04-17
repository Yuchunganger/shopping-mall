package com.judychen.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judychen.springbootmall.constant.ProductCategory;
import com.judychen.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/1");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200)) // OK
                .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.productId", equalTo(1)))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    public void getProduct_ProductNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 2000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404)); // Not Found
    }

    @Transactional
    @Test
    public void createProduct() throws Exception {
        // Java Object -> Json
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("testName");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http://test");
        productRequest.setPrice(500);
        productRequest.setStock(500);

        String json = objectMapper.writeValueAsString(productRequest);

        // Set Http Request Method & Request Body
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Call Request
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201)) // Created
                .andExpect(jsonPath("$.productId", notNullValue()))
                .andExpect(jsonPath("$.productName", equalTo("testName")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test")))
                .andExpect(jsonPath("$.price", equalTo(500)))
                .andExpect(jsonPath("$.stock", equalTo(500)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception{
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("testName");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400)); // Bad Request
    }

    @Transactional
    @Test
    public void updateProduct() throws Exception{
        // Java Object -> Json
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("testName");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http://test");
        productRequest.setPrice(500);
        productRequest.setStock(500);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productI}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200)) // OK
                .andExpect(jsonPath("$.productId", equalTo(1)))
                .andExpect(jsonPath("$.productName", equalTo("testName")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test")))
                .andExpect(jsonPath("$.price", equalTo(500)))
                .andExpect(jsonPath("$.stock", equalTo(500)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }


    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception{
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("testName");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Bad Request
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400)); // Bad Request
    }

    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception{
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("testName");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImageUrl("http://test");
        productRequest.setPrice(500);
        productRequest.setStock(500);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{id}", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Bad Request
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404)); // Not Found
    }


    @Test
    @Transactional
    public void deleteProduct_deleteExistingProduct() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204)); // No Content
    }

    @Test
    @Transactional
    public void deleteProduct_deleteNonExistingProduct() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{id}", 1000);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(204)); // No Content
    }

    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200)) // OK
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));
    }

    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "B")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200)) // OK
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200)) // OK
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.results[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.results[4].productId", equalTo(2)));
    }
}