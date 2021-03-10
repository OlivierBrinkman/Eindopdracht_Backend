package com.customermanagment.sims.service.customer;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import com.customermanagment.sims.repository.customer.Customer_Address_Repository;
import com.customermanagment.sims.repository.customer.Customer_Repository;
import com.customermanagment.sims.service.order.Order_Service_Implementation;
import com.customermanagment.sims.utility.Utility;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Customer Service implementation.
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Service
public class Customer_Service_Implementation implements Customer_Service {

    Utility utility = new Utility();

    final Customer_Repository customerRepo;
    final Customer_Address_Repository addressRepo;
    final Order_Service_Implementation orderService;

    /**
     * service constructor
     * @param customerRepo
     * @param addressRepo
     * @param orderService
     */
    public Customer_Service_Implementation(Customer_Repository customerRepo, Customer_Address_Repository addressRepo, Order_Service_Implementation orderService) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
        this.orderService = orderService;
    }

    /**
     * get all customers
     * @return
     */
    @Override
    public List<Customer> getAllCustomers() {
            return customerRepo.findAll();
    }

    /**
     * get customer by id
     * @param id
     * @return
     */
    @Override
    public Customer getCustomerById(long id) {
            return customerRepo.findById(id).get();
    }

    /**
     * creates customer
     * @param customer
     * @return
     */
    @Override
    public long createCustomer(Customer customer) {
            Date temp_now =  java.sql.Date.valueOf(java.time.LocalDate.now());
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String now = DateFor.format(temp_now);
            customer.setCustomerSince(now);

            return customerRepo.save(customer).getId();
    }

    /**
     * deletes customer
     * @param id
     */
    @Override
    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    /**
     * creates customer address
     * @param customerAddress
     * @return
     */
    @Override
    public long createCustomerAddress(Customer_Address customerAddress) {
            return addressRepo.save(customerAddress).getId();
    }

    /**
     * deletes customer address
     * @param customerAddressId
     */
    @Override
    public void deleteCustomerAddress(long customerAddressId) {
            addressRepo.deleteById(customerAddressId);
    }

    /**
     * get customer address by customer id
     * @param customerId
     * @return
     */
    @Override
    public Customer_Address getCustomerAddressByCustomerId(long customerId) {
            Customer_Address customerAddresses = new Customer_Address();
        for(Customer_Address ca : addressRepo.findAll())
        {
            if(ca.getCustomerId() == customerId)
            {
                customerAddresses = ca;
            }
        }
        return customerAddresses;
    }

    /**
     * inserts dummy customers
     */
    @Override
    public void insertCustomers(){
    utility.insertCustomers(customerRepo, addressRepo);
}

    /**
     * deletes all customers
     */
    @Override
    public void deleteCustomers(){
    orderService.deleteOrders();
    addressRepo.deleteAll();
    customerRepo.deleteAll();
}
}
