package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepo customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     *
     *
     * @param customer
     * @return
     */
    @Override
    public Customer createCustomer(Customer customer) {
        Customer nullEmail = customerRepo.findByEmail(customer.getEmail());
        Customer nullUsername = customerRepo.findByUsername(customer.getUsername());

        if (nullEmail == null && nullUsername == null){
            return customerRepo.save(customer);
        }

        return null;
    }

    /**
     *
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Customer validateCredentials(String username, String password) {
        Customer userLoggingIn = this.customerRepo.findByUsername(username);


        if(userLoggingIn == null)
            return null;


        if(!password.equals(userLoggingIn.getPassword()))
            return null;

        return userLoggingIn;
    }

    /**
     *
     *
     * @param customerId
     * @return
     */
    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerRepo.findById(customerId).orElse(null);
    }

    /**
     *
     *
     *
     * @param username
     * @return
     */
    @Override
    public Customer getCustomerByUsername(String username) {
        return this.customerRepo.findByUsername(username);
    }
}
