package com.customermanagment.sims.controller;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import com.customermanagment.sims.service.customer.Customer_Service_Implementation;
import com.customermanagment.sims.service.inventory.Inventory_Service_Implementation;
import com.customermanagment.sims.service.order.Order_Service_Implementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * Customer_Order Controller
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Controller
public class Customer_Order_Controller {

    int orderProductsListSize = 0;
    int totalOrderPrice = 0;
    long customerId = 0;

    final Inventory_Service_Implementation inventoryService;
    final Customer_Service_Implementation customerService;
    final Order_Service_Implementation orderService;

    /**
     * services constructor
     * @param inventoryService
     * @param customerService
     * @param orderService
     */
    public Customer_Order_Controller(Inventory_Service_Implementation inventoryService, Customer_Service_Implementation customerService, Order_Service_Implementation orderService) {
        this.inventoryService = inventoryService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    /**
     * navigates to customerdetails page for order
     * @param model
     * @return
     */
    @GetMapping("/placeOrder/1")
    public String Index(Model model){
        orderProductsListSize = 0;totalOrderPrice = 0;
        model.addAttribute("newCustomer", new Customer());
        model.addAttribute("newCustomerAddress", new Customer_Address());
        return"order/Order_Create_Customer_Details";
    }

    /**
     * sets given details as selected customer for step 2
     * @param customer
     * @param customerAddress
     * @param model
     * @return
     */
    @PostMapping("/placeOrder/1")
    public String setCustomerDetails(@ModelAttribute Customer customer, @ModelAttribute Customer_Address customerAddress, Model model) {
        customerId = customerService.createCustomer(customer);
        customerAddress.setCustomerId(customerId);
        customerService.createCustomerAddress(customerAddress);
        System.out.println(customerId);
        model.addAttribute("newCustomer", customerService.getCustomerById(customerId));
        model.addAttribute("newCustomerAddress", customerService.getCustomerAddressByCustomerId(customerId));
        return"order/Order_Create_Customer_Details";
    }
}
