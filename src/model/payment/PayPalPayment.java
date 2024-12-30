package model.payment;

import model.Customer;

public class PayPalPayment implements PaymentStrategy {

    private String paypalEmail;

    public PayPalPayment(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using PayPal.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        System.out.println("Applying discount of " + discount + " on PayPal payment.");
    }
}
