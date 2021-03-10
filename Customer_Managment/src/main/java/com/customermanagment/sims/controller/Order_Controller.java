package com.customermanagment.sims.controller;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import com.customermanagment.sims.model.tables.order.Order;
import com.customermanagment.sims.model.tables.order.Order_Product;
import com.customermanagment.sims.service.customer.Customer_Service_Implementation;
import com.customermanagment.sims.service.inventory.Inventory_Service_Implementation;
import com.customermanagment.sims.service.order.Order_Service_Implementation;
import com.customermanagment.sims.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Order Controller
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Controller
public class Order_Controller {

    Utility utility = new Utility(); Order order = new Order(); List<Long> productsToOrder = new ArrayList<>();
    int orderProductsListSize = 0; int totalOrderPrice = 0;

    final Inventory_Service_Implementation inventoryService;
    final Customer_Service_Implementation customerService;
    final Order_Service_Implementation orderService;

    /**
     * services constructor
     * @param inventoryService
     * @param customerService
     * @param orderService
     */
    public Order_Controller(Inventory_Service_Implementation inventoryService, Customer_Service_Implementation customerService, Order_Service_Implementation orderService) {
        this.inventoryService = inventoryService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    /**
     * return orders page
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String Index(Model model){
        productsToOrder.clear();
        orderService.clearIncompleteOrders();
        model.addAttribute("numberOfOrders", orderService.getOrders().size() + " orders");
        return navigate_Orders(model);
    }

    /**
     * returns step 1 page
     * @param model
     * @return
     */
    @GetMapping("/order/step1")
    public String navigateOrder(Model model) {
        return navigate_Step1(model);
    }

    /**
     * submits selected customer then returns page 2 of order process
     * @param customerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/step2", method = RequestMethod.GET)
    public String customerSelected(@RequestParam(name="customerId")long customerId, Model model) {
            orderProductsListSize = 0;
            totalOrderPrice = 0;
            productsToOrder.clear();
            order.setDateCreated(utility.getCurrentDate());
            order.setCustomerId(customerId);
            return navigate_Step2(model, customerId);
    }

    /**
     * adds product to order as orderProduct
     * @param productId
     * @param model
     * @return
     */
    @GetMapping(value = "/order/add")
    public String addProductToOrder(@RequestParam(name="productId")long productId, Model model) {
        productsToOrder.add(productId);
        totalOrderPrice = totalOrderPrice + inventoryService.getProductById(productId).getPrice();
        orderProductsListSize++;
        model.addAttribute("productsListSize",  + orderProductsListSize);
        model.addAttribute("totalPrice", "€" +totalOrderPrice+ ",-");
        model.addAttribute("products", inventoryService.getAvailableProducts());
        return "order/Order_Create_Step2";
    }

    /**
     * submits order
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/order/summary")
    public String submitOrder(Model model) throws Exception {
        if(order.getCustomerId() > 0) {
            orderService.createOrder(order);
            return navigate_OrderSummary(model, order.getId());
        }
        else {
            return navigate_Orders(model);
        }
    }

    /**
     * show order details
     * @param orderId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orders/details")
    public String orderDetails(@RequestParam(name="orderId")long orderId, Model model) throws Exception {
        return navigate_OrderSummary(model, orderId);
    }

    /**
     * deletes order
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping(value = "/orders/delete")
    public String deleteOrder(@RequestParam(name="orderId")long orderId, Model model) {
        orderService.deleteOrder(orderId);
        return navigate_Orders(model);
    }

    /**
     * inserts dummy data
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/orders/insert/records")
    public String insertDummyRecords(Model model) throws Exception {
        orderService.insertOrders();
        return "redirect:/orders";
    }

    /**
     * delets all orders
     * @param model
     * @return
     */
    @GetMapping("/orders/delete/all")
    public String deleteAllRecords(Model model) {
        orderService.deleteOrders();
        return "redirect:/orders";
    }

    /**
     * navigates to orders
     * @param model
     * @return
     */
    public String navigate_Orders(Model model) {
        model.addAttribute("orders",orderService.getOrders());
        return "order/Orders";
    }

    /**
     * navigates to step 1 page
     * @param model
     * @return
     */
    public String navigate_Step1(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order/Order_Create_Step1";
    }

    /**
     * navigates to step 2 page
     * @param model
     * @param customerId
     * @return
     */
    public String navigate_Step2(Model model, long customerId) {
        model.addAttribute("customerId", customerId);
        model.addAttribute("products", inventoryService.getAvailableProducts());
        return "order/Order_Create_Step2";
    }

    /**
     * navigates to order summary page
     * @param model
     * @param orderId
     * @return
     * @throws Exception
     */
    public String navigate_OrderSummary(Model model, long orderId) throws Exception {
        Order selectedOrder = orderService.getOrderById(orderId);

        Customer customer = customerService.getCustomerById(selectedOrder.getCustomerId());
        Customer_Address customerAddress = customerService.getCustomerAddressByCustomerId(customer.getId());

        for(Long selectedId : productsToOrder) {
            Order_Product orderProduct = new Order_Product();
            orderProduct.setOrderId(selectedOrder.getId());
            orderProduct.setProductId(selectedId);
            orderService.createOrderProduct(orderProduct);
        }
        String orderPrice ="€" + utility.calculateTotalByOrderId(orderId, orderService);
        selectedOrder.setTotalPrice((int)utility.calculateTotalByOrderId(orderId, orderService));
        orderService.createOrder(selectedOrder);
        model.addAttribute("order", orderService.getOrderById(selectedOrder.getId()));
        model.addAttribute("customer", customer);
        model.addAttribute("customerAddress", customerAddress);
        model.addAttribute("orderPrice" , orderPrice);
        model.addAttribute("orderSummary", orderService.getSummaryStructure(orderId));
        model.addAttribute("products", orderService.getProductsForSummary(selectedOrder.getId()));
        return "order/Order_Submit_Summary";
    }


}
