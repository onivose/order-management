package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     *
     * @return
     */
    List<Order> getAllOrders();

    /**
     *
     * @return
     */
    List<Order> findAllWithTotalGreaterThan(Double total);

    /**
     *
     * @return
     */
    List<Order> findAllWithTotalLessThan(Double total);

    /**
     *
     * @param customerId
     * @return
     */
    List<Order> getAllOrdersByCustomerId(Integer customerId);

    /**
     *
     * @param orderId
     * @return
     */
    Order getOrderById(Integer orderId);

    /**
     *
     * @param customerId
     * @return
     */
    Order beginNewOrder(Integer customerId);

    Order submitOrder(List<Integer> productIds, Integer orderId);
}
