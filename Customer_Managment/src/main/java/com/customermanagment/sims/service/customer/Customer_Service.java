package com.customermanagment.sims.service.customer;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import java.util.List;
/**
 * Customer Service
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public interface Customer_Service {

     List<Customer> getAllCustomers();
     Customer getCustomerById(long customerId);
     Customer_Address getCustomerAddressByCustomerId(long customerId);

     long createCustomer(Customer customer);
     long createCustomerAddress(Customer_Address customerAddress);

     void deleteCustomer(long customerId);
     void deleteCustomerAddress(long customerAddressId);
     void insertCustomers();
     void deleteCustomers();

}
