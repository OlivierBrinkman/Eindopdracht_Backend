package com.customermanagment.sims.model.tables.order;
import javax.persistence.*;
/**
 * Order Entity
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Entity
@Table(name = "ORDERS")
public class Order {

    //Attributes
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "ID") private long id;
    @Column(name = "CUSTOMER_ID") private long customerId;
    @Column(name = "TOTAL_PRICE") private int totalPrice;
    @Column(name = "DATE_CREATED") private String dateCreated;

    //Constructors
    public Order(long id, long customerId, int totalPrice, String dateCreated) {
        this.id = id;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
    }
    public Order() { }

    //Getters and Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    //To string
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }

}
