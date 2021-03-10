package com.customermanagment.sims.controller;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import com.customermanagment.sims.service.customer.Customer_Service_Implementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * Customer Controller
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Controller
public class Customer_Controller {

    final Customer_Service_Implementation customerService;

    /**
     * service constructor
     * @param customerService
     */
    public Customer_Controller(Customer_Service_Implementation customerService) {
        this.customerService = customerService;
    }

    /**
     * return customers
     * @param model
     * @return
     */
    @GetMapping(value = "/customers")
    public String Index_Customers(Model model) {
        model.addAttribute("numberOfCustomers", customerService.getAllCustomers().size() + " customers");
            model.addAttribute("customers", customerService.getAllCustomers());
            return "customer/Customers";
    }

    /**
     * navigates to Create page
     * @param model
     * @return
     */
    @GetMapping(value = "/customers/new")
    public String Customer_New(Model model) {
            model.addAttribute("newCustomer", new Customer());
            model.addAttribute("newCustomerAddress", new Customer_Address());
            return "customer/Customer_Create";
    }

    /**
     * creates Customer
     * @param customer
     * @param customerAddress
     * @return
     */
    @PostMapping(value = "/customers/new")
    public String Customer_Save(@ModelAttribute Customer customer, @ModelAttribute Customer_Address customerAddress){
            customerService.createCustomer(customer);
            customerAddress.setCustomerId(customer.getId());
            customerService.createCustomerAddress(customerAddress);
            return "redirect:/customers";
    }

    /**
     * navigates to Update page
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping(value = "/customers/edit")
    public String selectCustomer(@RequestParam(name="customerId")long customerId, Model model) {
        if(customerId == 0) { return "redirect:/customers"; }
        model.addAttribute("selectedCustomer", customerService.getCustomerById(customerId));
        model.addAttribute("selectedCustomerAddress", customerService.getCustomerAddressByCustomerId(customerId));
        return "customer/Customer_Update";
    }

    /**
     * edits Customer
     * @param customer
     * @param customerAddress
     * @param result
     * @param model
     * @return
     */
    @PostMapping(value = "/customers/edit/{id}", consumes = "application/x-www-form-urlencoded")
    public String updateCustomer(Customer customer,Customer_Address customerAddress, BindingResult result, Model model) {
        if(!model.containsAttribute("selectedCustomer")) { return "redirect:/customers"; }
        else { if (result.hasErrors()) { return "redirect:/customers"; } }

        customerService.createCustomer(customer);
        customerService.createCustomerAddress(customerAddress);

        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/Customers";
    }

    /**
     * deletes Customer
     * @param customerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/customers/delete" ,method = RequestMethod.GET)
    public String Customer_Delete(@RequestParam(name="customerId")long customerId, Model model) {
        customerService.deleteCustomer(customerId);
        model.addAttribute(customerService.getAllCustomers());
        return "redirect:/customers";
    }

    /**
     * inserts dummy data
     * @param model
     * @return
     */
    @GetMapping(value = "/customers/insert/data")
    public String insertCustomers(Model model) {
        customerService.insertCustomers();
        model.addAttribute("customers", customerService.getAllCustomers());
        return "redirect:/customers";
    }

    /**
     * delets all customers
     * @param model
     * @return
     */
    @GetMapping(value = "/customers/delete/all")
    public String deleteCustomers(Model model) {
        customerService.deleteCustomers();
        model.addAttribute("customers", customerService.getAllCustomers());
        return "redirect:/customers";
    }
}
