package com.customermanagment.sims.service.order;
import com.customermanagment.sims.model.tables.order.Order;
import com.customermanagment.sims.model.tables.order.Order_Product;
import com.customermanagment.sims.model.tables.product.Product;
import com.customermanagment.sims.model.structures.Product_Structure;
import com.customermanagment.sims.model.structures.Order_Summary_Structure;
import java.util.List;
/**
 * Order Service.
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public interface Order_Service {

     List<Order> getOrders();
     List<Product> getProductsByOrderId(long orderId) throws Exception;
     List<Product_Structure> getProductsForSummary(long orderId);
     Product_Structure getProductSummary(long productId);
     Order_Summary_Structure getSummaryStructure(long orderId) throws Exception;
     Order getOrderById(long orderId);

     void createOrder(Order order);
     void deleteOrder(long orderId);
     void createOrderProduct(Order_Product orderProduct);
     void deleteOrderProductsByOrderId(long orderId);
     void deleteOrders();
     void insertOrders() throws Exception;
     void clearIncompleteOrders();
}
