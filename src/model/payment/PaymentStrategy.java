package model.payment;

import model.Customer;

public interface PaymentStrategy {
    boolean pay(double amount);
    void applyDiscount(Customer customer, double discount);
}