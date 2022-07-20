package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Product;

import java.util.List;

public interface ProductService {

    void getInitialProducts();

    Product createProduct(Product product);

    /**
     *
     * @param productId
     * @return order
     */
    Product getProductById(Integer productId);

    void updateProduct(Product product);

    List<Product> getAllAvailableProducts();


}
