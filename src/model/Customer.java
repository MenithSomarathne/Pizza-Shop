package model;

import model.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String phoneNumber;
    private String email;
    private int loyaltyPoints;
    private PaymentStrategy paymentStrategy;
    private List<Pizza> favoritePizzas;

    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = 0;
        this.favoritePizzas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean makePayment(double amount) {
        double discount = 0;
        if (this.paymentStrategy instanceof model.payment.CardPayment) {
            discount = 10; // Example: 10% discount for card payments
        }
        paymentStrategy.applyDiscount(this, discount);
        return paymentStrategy.pay(amount - discount);
    }

    public void addFavoritePizza(Pizza pizza) {
        if (!favoritePizzas.contains(pizza)) {
            favoritePizzas.add(pizza);
            System.out.println("Pizza added to favorites: " + pizza);
        } else {
            System.out.println("This pizza is already in your favorites.");
        }
    }

    public void removeFavoritePizza(Pizza pizza) {
        if (favoritePizzas.remove(pizza)) {
            System.out.println("Pizza removed from favorites: " + pizza);
        } else {
            System.out.println("Pizza not found in favorites.");
        }
    }

    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

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

    @Override
    public String toString() {
        return "Customer{name='" + name + "', phoneNumber='" + phoneNumber + "', email='" + email + "', loyaltyPoints=" + loyaltyPoints + '}';
    }
}
