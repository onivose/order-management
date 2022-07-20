package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;

public interface CustomerService {
    /**
     *
     *
     * @param customer
     * @return
     */
    Customer createCustomer(Customer customer);
    /**
     *
     * @param username
     * @param password
     * @return
     * */
    Customer validateCredentials (String username, String password);

    /**
     *
     * @param customerId
     * @return
     */
    Customer getCustomerById(Integer customerId);

    /**
     *
     * @param username
     * @return
     */
    Customer getCustomerByUsername(String username);

}
