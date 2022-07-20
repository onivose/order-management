package com.hexaware.ordermanagement.controller;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.service.ProductService;
import com.hexaware.ordermanagement.util.JsonResponse;
import com.hexaware.ordermanagement.model.Order;
import com.hexaware.ordermanagement.service.CustomerService;
import com.hexaware.ordermanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5502" })
public class OrderController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService, ProductService productService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
    }

    //WORKS
    @PostMapping("{customerId}")
    public ResponseEntity<JsonResponse> beginNewOrder(@PathVariable Integer customerId){

        Customer author = customerService.getCustomerById(customerId);
        Order newOrder = orderService.beginNewOrder(customerId);
        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully created", newOrder);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);

    }

    //WORKS
    @PostMapping("submit/{orderId}")
    public ResponseEntity<JsonResponse> submitOrder(@RequestBody List<Integer> productIds, @PathVariable Integer orderId){

        logger.info("REQUEST: " + "--POST-- api/v1/order @ " + LocalDateTime.now());

        Order orderToSubmit = orderService.getOrderById(orderId);

        Order submittedOrder = orderService.submitOrder(productIds, orderId);

        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully submitted", submittedOrder);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    //WORKS
    @GetMapping
    public ResponseEntity<JsonResponse> getAllOrders(){

        logger.info("REQUEST: " + "--GET-- api/v1/order @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrders();

        jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    //WORKS
    /**
     *
     *
     *
     * @param total
     * @return
     */
    @GetMapping("/greaterThan/{total}")
    ResponseEntity<JsonResponse> findAllWithTotalGreaterThan(@PathVariable Double total){
        logger.info("REQUEST: " + "--GET-- api/v1/order/greaterThan/" + total + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.findAllWithTotalGreaterThan(total);

        Integer numOfOrders = orderListFromDb.size();
        jsonResponse = new JsonResponse(true,
                numOfOrders + " order(s) with total greater than $"+ total + " successfully retrieved",
                orderListFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    /**
     *
     *
     *
     *
     * @param total
     * @return
     */
    @GetMapping("/lessThan/{total}")
    ResponseEntity<JsonResponse> findAllWithTotalLessThan(@PathVariable Double total){
        logger.info("REQUEST: " + "--GET-- api/v1/order/lessThan/" + total + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.findAllWithTotalLessThan(total);

        Integer numOfOrders = orderListFromDb.size();
        jsonResponse = new JsonResponse(true,
                numOfOrders + " order(s) with total less than $"+ total + " successfully retrieved",
                orderListFromDb);
        return ResponseEntity.ok(jsonResponse);

    }

    //WORKS
    /**
     * @param orderId
     * @return
     */
    @GetMapping("{orderId}")
    public ResponseEntity<JsonResponse> getOrderById(@PathVariable Integer orderId){

        logger.info("REQUEST: " + "--GET-- api/v1/order/" + orderId + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        Optional<Order> orderFromDb = Optional.ofNullable(orderService.getOrderById(orderId));

        jsonResponse = new JsonResponse(true, "Order with order Id: " + orderId + " found", orderFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    //WORKS
    @GetMapping("customer/{customerId}")
    public ResponseEntity<JsonResponse> getAllOrdersForCustomerWithId(@PathVariable Integer customerId){
        logger.info("REQUEST: " + "--GET-- api/v1/order/customer/" + customerId + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrdersByCustomerId(customerId);

        jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);

    }
}
