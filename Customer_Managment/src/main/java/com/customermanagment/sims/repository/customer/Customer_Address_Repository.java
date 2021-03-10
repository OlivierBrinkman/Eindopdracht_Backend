package com.customermanagment.sims.repository.customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Customer_Address Repository
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Repository
public interface Customer_Address_Repository extends JpaRepository<Customer_Address, Long> {
    //CUSTOMER_ADDRESS REPOSITORY
}