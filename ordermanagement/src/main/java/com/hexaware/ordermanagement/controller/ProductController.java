package com.hexaware.ordermanagement.controller;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.service.ProductService;
import com.hexaware.ordermanagement.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5502" })
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     *
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<JsonResponse> populateProductsInDb(){
        productService.getInitialProducts();
        return new ResponseEntity<>(new JsonResponse(true, "All products are now in Database",null),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllAvailableProducts(){
        List<Product> availableProducts =  productService.getAllAvailableProducts();

        return new ResponseEntity<>(new JsonResponse(true, "All available products retrieved",availableProducts),
                HttpStatus.OK);
    }
}
