package model.payment;

import model.Customer;

public class CardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using Card.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        System.out.println("Applying discount of " + discount + " on Card payment.");
    }
}
