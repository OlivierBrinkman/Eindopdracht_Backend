package com.customermanagment.sims.repository.order;
import com.customermanagment.sims.model.tables.order.Order_Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Order_Product Repository
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Repository
public interface Order_Product_Repository extends JpaRepository<Order_Product, Long> {
        //ORDER PRODUCT REPOSITORY

    List<Order_Product> findAllByOrderId(Long orderId);
    void deleteOrder_ProductByOrderId(Long orderId);

}
