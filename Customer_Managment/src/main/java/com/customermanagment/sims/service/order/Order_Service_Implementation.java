package com.customermanagment.sims.service.order;
import com.customermanagment.sims.model.tables.customer.Customer;
import com.customermanagment.sims.model.structures.Order_Summary_Structure;
import com.customermanagment.sims.model.tables.order.Order;
import com.customermanagment.sims.model.tables.order.Order_Product;
import com.customermanagment.sims.model.tables.product.Brand;
import com.customermanagment.sims.model.tables.product.Product;
import com.customermanagment.sims.model.structures.Product_Structure;
import com.customermanagment.sims.repository.customer.Customer_Address_Repository;
import com.customermanagment.sims.repository.customer.Customer_Repository;
import com.customermanagment.sims.repository.order.Order_Product_Repository;
import com.customermanagment.sims.repository.order.Order_Repository;
import com.customermanagment.sims.repository.product.Brand_Repository;
import com.customermanagment.sims.repository.product.Product_Repository;
import com.customermanagment.sims.service.inventory.Inventory_Service_Implementation;
import com.customermanagment.sims.utility.Utility;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Order Service implementation.
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Service
public class Order_Service_Implementation implements Order_Service{

    Utility utility = new Utility();

    final Order_Repository orderRepository; final Order_Product_Repository orderProductRepository;
    final Inventory_Service_Implementation inventoryService;
    final Brand_Repository brandRepository;
    final Product_Repository productRepository;
    final Customer_Repository customerRepository;final Customer_Address_Repository customerAddressRepository;

    /**
     * service constructor
     * @param orderRepository
     * @param orderProductRepository
     * @param inventoryService
     * @param customerRepository
     * @param customerAddressRepository
     * @param brandRepository
     * @param productRepository
     */
    public Order_Service_Implementation(Order_Repository orderRepository, Order_Product_Repository orderProductRepository, Inventory_Service_Implementation inventoryService, Customer_Repository customerRepository, Customer_Address_Repository customerAddressRepository, Brand_Repository brandRepository, Product_Repository productRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.inventoryService = inventoryService;
        this.customerRepository = customerRepository;

        this.customerAddressRepository = customerAddressRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    /**
     * creates order
     * @param order
     */
    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    /**
     * deletes order by id
     * @param orderId
     */
    @Override
    public void deleteOrder(long orderId) {
        deleteOrderProductsByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    /**
     * get order by id
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).get();
    }

    /**
     * get all orders
     * @return
     */
    @Override
    public List<Order> getOrders () {
        return orderRepository.findAll();
    }

    /**
     * creates order product
     * @param orderProduct
     */
    @Override
    public void createOrderProduct(Order_Product orderProduct) {
        orderProductRepository.save(orderProduct);
        Product p = inventoryService.getProductById(orderProduct.getProductId());
        p.setAmount(p.getAmount() - 1);
        inventoryService.createOrUpdateProduct(p);
    }

    /**
     * delete order products by order id
     * @param orderId
     */
    @Override
    public void deleteOrderProductsByOrderId(long orderId) {
        List<Order_Product> allOrderProducts = orderProductRepository.findAll();
        Order order = getOrderById(orderId);
        for(Order_Product op : allOrderProducts)
        {
            if(op.getOrderId() == orderId)
            {
                orderProductRepository.deleteById(op.getId());
            }
        }
    }

    /**
     * get products by order id
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> getProductsByOrderId(long orderId) throws Exception {
        List<Product> products = new ArrayList<>();
        for(Order_Product op : orderProductRepository.findAll())
        {
            if(op.getOrderId() == orderId)
            {
               try {
                   Product p = inventoryService.getProductById(op.getProductId());
                   products.add(p);
               }
               catch (Exception e){
                   throw new Exception("Some products dont exists anymore, your order will be deleted.");
               }

            }
        }
        return products;
    }

    /**
     * get product summary by product id
     * @param productId
     * @return
     */
    @Override
    public Product_Structure getProductSummary(long productId) {
        Product product = inventoryService.getProductById(productId);
        Brand brand = inventoryService.GetBrandByProductId(productId);
        Product_Structure productSummary = new Product_Structure();
        String price = String.valueOf(product.getPrice());

        productSummary.setID(product.getId());
        productSummary.setName(product.getName());
        productSummary.setDescription(product.getDescription());
        productSummary.setPrice("€" + price + ",-");
        productSummary.setBrand(brand.getName());

        return productSummary;
    }

    /**
     * get products by order id for order summary
     * @param orderId
     * @return
     */
    @Override
    public List<Product_Structure> getProductsForSummary(long orderId) {
        List<Product_Structure> productSummaries = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        long counter = 1;
        try {
            products = getProductsByOrderId(orderId);
            for(Product product : products)
            {
                Product_Structure productSummary = getProductSummary(product.getId());
                productSummary.setID(counter);
                productSummaries.add(productSummary);
                counter++;
            }
        }
        catch (Exception e) { }

        return productSummaries;
    }

    /**
     * get structure for order summary
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public Order_Summary_Structure getSummaryStructure(long orderId) throws Exception {
        NumberFormat formatter = new DecimalFormat("#0.00");
        Order_Summary_Structure orderSummaryStructure = new Order_Summary_Structure();
        Order order = orderRepository.findById(orderId).get();
        int productCounter = getProductsByOrderId(orderId).size();

        orderSummaryStructure.setTotalPrice(order.getTotalPrice());
        orderSummaryStructure.setExVAT(formatter.format(utility.calculateTotalExVat(order.getTotalPrice())));
        orderSummaryStructure.setVAT(formatter.format(utility.calculateVAT(order.getTotalPrice())));
        orderSummaryStructure.setAmountProducts(productCounter);
        orderSummaryStructure.setNewInventory(inventoryService.calculateInventoryValue());

        return orderSummaryStructure;
    }

    /**
     * deletes all orders
     */
    @Override
    public void deleteOrders(){
        utility.deleteOrders(orderRepository, orderProductRepository);
    }

    /**
     * inserts dummy orders
     * @throws Exception
     */
    @Override
    public void insertOrders() throws Exception {
        List<Customer> customers = customerRepository.findAll();
        List<Product> products = inventoryService.getProducts();
        if(customers.isEmpty()){utility.insertCustomers(customerRepository, customerAddressRepository);}
        if(products.isEmpty()){ utility.insertInventory(brandRepository, productRepository);}


        utility.insertOrders(customers, products ,orderProductRepository,orderRepository, this);
    }

    /**
     * deletes all incomplete orders (validation function triggered when a order process stopped
     */
    @Override
    public void clearIncompleteOrders() {
            List<Order> orders = orderRepository.findAll();
            for(Order order : orders) {
                if(order.getTotalPrice() == 0) {
                    orderRepository.deleteById(order.getId());
                }
            }
    }


}
