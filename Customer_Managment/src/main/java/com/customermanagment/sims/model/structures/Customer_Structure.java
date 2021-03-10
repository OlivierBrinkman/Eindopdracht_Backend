package com.customermanagment.sims.model.structures;

import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;

/**
 * Customer_Combined Entity
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public class Customer_Structure {

    //Attributes
    public Customer customer;
    public Customer_Address customerAddress;

    //Constructor
    public Customer_Structure(Customer customer, Customer_Address customerAddress) {
        this.customer = customer;
        this.customerAddress = customerAddress;
    }
}
