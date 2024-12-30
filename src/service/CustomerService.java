package service;

import model.Customer;

public interface CustomerService {
    Customer createCustomer(String name, String phoneNumber, String email);

    Customer getCustomerByPhoneNumber(String phoneNumber);

    void updateLoyaltyPoints(String phoneNumber, int points);

    void deductLoyaltyPoints(String phoneNumber, int points);
}
