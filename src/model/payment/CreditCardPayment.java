package model.payment;

import model.Customer;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using Credit Card.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        // Apply any promotion or discount logic for Credit Card payments
        System.out.println("Applying discount of " + discount + " on Credit Card payment.");
        // Discount logic
    }
}
