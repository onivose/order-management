package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.Order;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.repository.CustomerRepo;
import com.hexaware.ordermanagement.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo, ProductService productService) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productService = productService;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public List<Order> getAllOrders() {
        return this.orderRepo.findAll();
    }

    /**
     *
     *
     * @param total
     * @return
     */
    @Override
    public List<Order> findAllWithTotalGreaterThan(Double total) {
        return this.orderRepo.findByTotalGreaterThan(total);
    }

    /**
     *
     *
     * @param total
     * @return
     */
    @Override
    public List<Order> findAllWithTotalLessThan(Double total) {
        return orderRepo.findByTotalLessThan(total);
    }

    @Override
    public List<Order> getAllOrdersByCustomerId(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);

        return customer != null ? this.orderRepo.getByCustomer(customer) : null;

    }

    @Override
    public Order getOrderById(Integer orderId) {
        return this.orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public Order beginNewOrder(Integer customerId) {

        Optional<Customer> author = customerRepo.findById(customerId);
        if (author.isPresent()){
            Order newOrder = new Order();
            return this.orderRepo.save(newOrder);
        }
        return null;
    }

    @Override
    public Order submitOrder(List<Integer> productIds, Integer orderId) {

        Order orderToSubmit = this.getOrderById(orderId);
        productService.getInitialProducts();
        List<Product> orderProducts = new ArrayList<>();

        for (int i = 0; i <= productIds.size(); i = i + 1){
            Product orderProduct = productService.getProductById(productIds.get(1));
            orderProduct.setOrderFk(orderToSubmit);
            productService.updateProduct(orderProduct);
        }

        orderToSubmit.setProducts(orderProducts);
        orderToSubmit.setTotal(null);
        orderToSubmit.setSubmitted(true);
        orderRepo.saveAndFlush(orderToSubmit);
        return this.getOrderById(orderToSubmit.getOrderId());
    }
}
