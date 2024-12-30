package service.impl;

import model.Customer;
import service.CustomerService;

import java.util.HashMap;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private Map<String, Customer> customers = new HashMap<>();

    @Override
    public Customer createCustomer(String name, String phoneNumber, String email) {
        if (customers.containsKey(phoneNumber)) {
            System.out.println("Customer with this phone number already exists.");
            return customers.get(phoneNumber);
        }

        Customer newCustomer = new Customer(name, phoneNumber, email);
        customers.put(phoneNumber, newCustomer);
        return newCustomer;
    }


    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customers.get(phoneNumber);
    }

    @Override
    public void updateLoyaltyPoints(String phoneNumber, int points) {
        Customer customer = customers.get(phoneNumber);
        if (customer != null) {
            customer.addLoyaltyPoints(points);
            System.out.println("Updated loyalty points for " + customer.getName() + ": " + customer.getLoyaltyPoints());
        } else {
            System.out.println("Customer not found.");
        }
    }

    @Override
    public void deductLoyaltyPoints(String phoneNumber, int points) {
        Customer customer = customers.get(phoneNumber);
        if (customer != null) {
            if (customer.getLoyaltyPoints() >= points) {
                customer.addLoyaltyPoints(-points); // Deduct points
                System.out.println("Deducted " + points + " loyalty points for " + customer.getName() + ".");
            } else {
                System.out.println("Not enough loyalty points for " + customer.getName() + ".");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

}
