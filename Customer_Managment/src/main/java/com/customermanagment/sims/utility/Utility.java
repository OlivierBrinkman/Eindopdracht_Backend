package com.customermanagment.sims.utility;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.tables.customer.Customer_Address;
import com.customermanagment.sims.model.tables.order.Order;
import com.customermanagment.sims.model.tables.order.Order_Product;
import com.customermanagment.sims.model.tables.product.Brand;
import com.customermanagment.sims.model.tables.product.Product;
import com.customermanagment.sims.repository.customer.Customer_Address_Repository;
import com.customermanagment.sims.repository.customer.Customer_Repository;
import com.customermanagment.sims.repository.order.Order_Product_Repository;
import com.customermanagment.sims.repository.order.Order_Repository;
import com.customermanagment.sims.repository.product.Brand_Repository;
import com.customermanagment.sims.repository.product.Product_Repository;
import com.customermanagment.sims.service.order.Order_Service_Implementation;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 *Utility
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Service
public class Utility {

    /**
     * empty constructor
     */
    public Utility() {}

    /**
     * inserts dummy customers
     * @param customerRepository
     * @param customerAddressRepository
     */
    public void insertCustomers(Customer_Repository customerRepository, Customer_Address_Repository customerAddressRepository) {

        //CUSTOMERS
        Customer customer1 = new Customer();customer1.setName("John Doe");customer1.setEmail("Johndoe@gmail.com");customer1.setPhone("0654944832");customer1.setCustomerSince("01/04/2019");
        Customer customer2 = new Customer();customer2.setName("Donald Duck");customer2.setEmail("Donald_100@live.nl");customer2.setPhone("0659488372");customer2.setCustomerSince("21/02/2018");
        Customer customer3 = new Customer();customer3.setName("Samantah Doe");customer3.setEmail("Samantah_Doe@hotmail.com");customer3.setPhone("0695844732");customer3.setCustomerSince("13/11/2020");

        //SAVE CUSTOMERS
        long C1_Id = customerRepository.save(customer1).getId();
        long C2_Id =customerRepository.save(customer2).getId();
        long C3_Id = customerRepository.save(customer3).getId();


        //CUSTOMER ADDRESS 1
        Customer_Address address1 = new Customer_Address();address1.setCustomerId(C1_Id);address1.setStreet("Applestraat");address1.setNumber("10");address1.setZipcode("1029FG");address1.setCity("Enschede");address1.setCountry("Nederland");

        //CUSTOMER ADDRESS 2
        Customer_Address address2 = new Customer_Address();address2.setCustomerId(C2_Id);address2.setStreet("Banaanweg");address2.setNumber("115");address2.setZipcode("8675AC");address2.setCity("Amsterdam");address2.setCountry("Nederland");

        //CUSTOMER ADDRESS 3
        Customer_Address address3 = new Customer_Address();address3.setCustomerId(C3_Id);address3.setStreet("Sterrenlaan");address3.setNumber("14");address3.setZipcode("1254BV");address3.setCity("Haalem");address3.setCountry("Nederland");

        //SAVE CUSTOMER ADDRESSES 1 2 3
        customerAddressRepository.save(address1);
        customerAddressRepository.save(address2);
        customerAddressRepository.save(address3);
    }

    /**
     * deletes all customers
     * @param customerRepository
     * @param customerAddressRepository
     * @param orderRepository
     * @param orderProductRepository
     */
    public void deleteCustomers(Customer_Repository customerRepository, Customer_Address_Repository customerAddressRepository, Order_Repository orderRepository, Order_Product_Repository orderProductRepository) {
        deleteOrders(orderRepository, orderProductRepository);
        customerAddressRepository.deleteAll();
        customerRepository.deleteAll();
    }

    /**
     * creates dummy inventory
     * @param brandRepository
     * @param productRepository
     */
    public void insertInventory(Brand_Repository brandRepository, Product_Repository productRepository) {

        Brand apple = new Brand();apple.setName("Apple");
        Brand samsung = new Brand();samsung.setName("Samsung");

        brandRepository.save(apple).getId();
        brandRepository.save(samsung).getId();

        Product product1 = new Product();product1.setName("iPhone 12 Mini");product1.setDescription("128GB - Ocean Blue");product1.setAmount(100);product1.setPrice(799);product1.setBrand(apple.getId());
        Product product2 = new Product(); product2.setName("iPhone 12 Pro"); product2.setDescription("512Gb - Deep Black"); product2.setAmount(100); product2.setPrice(999); product2.setBrand(apple.getId());
        Product product3 = new Product();product3.setName("Galaxy S20+");product3.setDescription("128GB - Grey");product3.setAmount(100);product3.setPrice(899);product3.setBrand(samsung.getId());
        Product product4 = new Product();product4.setName("Galaxy S21 Ultra");product4.setDescription("1TB - Deep Titanium");product4.setAmount(100);product4.setPrice(1199);product4.setBrand(samsung.getId());

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
    }

    /**
     * deletes all inventory
     * @param brandRepository
     * @param productRepository
     * @param orderRepository
     * @param orderProductRepository
     */
    public void deleteInventory(Brand_Repository brandRepository, Product_Repository productRepository, Order_Repository orderRepository, Order_Product_Repository orderProductRepository) {
        deleteOrders(orderRepository, orderProductRepository);
        productRepository.deleteAll();
        brandRepository.deleteAll();
    }

    /**
     * creates dummy orders
     * @param customers
     * @param products
     * @param orderProductRepository
     * @param orderRepository
     * @param orderService
     * @throws Exception
     */
    public void insertOrders(List<Customer> customers, List<Product> products, Order_Product_Repository orderProductRepository, Order_Repository orderRepository, Order_Service_Implementation orderService) throws Exception {

        for(Customer customer : customers)
        {
            Order order = new Order();
            order.setCustomerId(customer.getId());
            order.setDateCreated(getCurrentDate());
            order.setTotalPrice(0);
            long orderId = orderRepository.save(order).getId();

            for(Product product : products)
            {
                Order_Product orderProduct = new Order_Product();
                orderProduct.setOrderId(orderId);
                orderProduct.setProductId(product.getId());
                orderProductRepository.save(orderProduct);
            }
            order.setTotalPrice((int)calculateTotalByOrderId(orderId, orderService));
            orderRepository.save(order);
        }
    }

    /**
     * deletes all orders
     * @param orderRepository
     * @param orderProductRepository
     */
    public void deleteOrders(Order_Repository orderRepository, Order_Product_Repository orderProductRepository) {
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
    }

    /**
     * gets current date
     * @return
     */
    public String getCurrentDate() {
        Date temp_now =  java.sql.Date.valueOf(java.time.LocalDate.now());
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        return DateFor.format(temp_now);
    }

    /**
     * calculates total price of order by order id
     * @param orderId
     * @param orderService
     * @return
     * @throws Exception
     */
    public double calculateTotalByOrderId(long orderId, Order_Service_Implementation orderService) throws Exception {
        Order order = orderService.getOrderById(orderId);
        List<Product> products = orderService.getProductsByOrderId(orderId);
        double totalPrice = 0;
        for(Product p : products)
        {
            totalPrice = totalPrice + p.getPrice();
        }
        return totalPrice;
    }

    /**
     * calculates the total price of order without VAT (21%) by total price
     * @param total
     * @return
     */
    public double calculateTotalExVat(double total) {
        return  total / 1.21;
    }

    /**
     * calculates VAT (21%) of order by total price
     * @param value
     * @return
     */
    public double calculateVAT(double value) {
        return  value - calculateTotalExVat(value);
    }
}
