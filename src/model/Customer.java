package model;

import model.payment.CreditCardPayment;
import model.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String phoneNumber;
    private String email;
    private int loyaltyPoints;
    private PaymentStrategy paymentStrategy;
    private List<Pizza> favoritePizzas; // List to store favorite pizzas

    // Constructor to initialize customer
    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = 0; // New customers start with zero points
        this.favoritePizzas = new ArrayList<>(); // Initialize favorites list
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for loyalty points
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Method to add loyalty points
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    // Setter for payment strategy
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Method to make payment
    public boolean makePayment(double amount) {
        // Apply any discounts before processing payment
        double discount = 0;
        if (this.paymentStrategy instanceof CreditCardPayment) {
            discount = 10; // Example: 10% discount for credit card payments
        }
        paymentStrategy.applyDiscount(this, discount);

        // Process payment using the strategy
        return paymentStrategy.pay(amount - discount);
    }

    // Methods for managing favorite pizzas

    // Add a pizza to favorites
    public void addFavoritePizza(Pizza pizza) {
        if (!favoritePizzas.contains(pizza)) { // Avoid duplicate favorites
            favoritePizzas.add(pizza);
            System.out.println("Pizza added to favorites: " + pizza);
        } else {
            System.out.println("This pizza is already in your favorites.");
        }
    }

    // Remove a pizza from favorites
    public void removeFavoritePizza(Pizza pizza) {
        if (favoritePizzas.remove(pizza)) {
            System.out.println("Pizza removed from favorites: " + pizza);
        } else {
            System.out.println("Pizza not found in favorites.");
        }
    }

    // Get the list of favorite pizzas
    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

    // Display all favorite pizzas
    public void displayFavorites() {
        if (favoritePizzas.isEmpty()) {
            System.out.println("No favorite pizzas saved yet.");
        } else {
            System.out.println("Your Favorite Pizzas:");
            for (int i = 0; i < favoritePizzas.size(); i++) {
                System.out.println((i + 1) + ". " + favoritePizzas.get(i));
            }
        }
    }

    // Override toString for displaying customer details
    @Override
    public String toString() {
        return "Customer{name='" + name + "', phoneNumber='" + phoneNumber + "', email='" + email + "', loyaltyPoints=" + loyaltyPoints + '}';
    }
}
