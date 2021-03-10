package com.customermanagment.sims.repository.customer;

import com.customermanagment.sims.model.tables.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Customer Repository
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */

@Repository
public interface Customer_Repository  extends JpaRepository<Customer, Long> {



}
