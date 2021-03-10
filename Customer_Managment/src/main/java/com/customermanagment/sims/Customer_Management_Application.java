package com.customermanagment.sims;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Customer_Managment_Application class.
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2021
 */
@SpringBootApplication
@ImportResource("classpath:app-config.xml")
public class Customer_Management_Application {
    public static void main(String[] args) {
        SpringApplication.run(Customer_Management_Application.class, args);
    }
}
