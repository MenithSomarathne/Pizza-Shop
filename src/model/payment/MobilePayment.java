package model.payment;

import model.Customer;

public class MobilePayment implements PaymentStrategy {

    private String mobileNumber;

    public MobilePayment(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean pay(double amount) {
        // Process payment using mobile payment system
        System.out.println("Paying " + amount + " using Mobile Payment.");
        // You can integrate with an actual mobile payment API
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        // Apply any promotion or discount logic for mobile payments
        System.out.println("Applying discount of " + discount + " on Mobile Payment.");
    }
}
