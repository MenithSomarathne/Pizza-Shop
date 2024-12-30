package model;

import model.enums.PizzaSize;
import model.enums.PizzaTopping;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private PizzaSize size;
    private String crust;
    private String sauce;
    private List<PizzaTopping> toppings;
    private String cheese;

    // Add price field
    private double price;

    private Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.price = calculatePrice(); // Set price upon creation
    }

    // Method to calculate price
    private double calculatePrice() {
        double basePrice = 800;
        switch (this.size) {
            case SMALL:
                basePrice = 800;
                break;
            case REGULAR:
                basePrice = 1300;
                break;
            case LARGE:
                basePrice = 1800;
                break;
        }

        // Add price for crust type
        if (this.crust.equals("Thick Crust")) {
            basePrice += 100; // Extra for thick crust
        } else if (this.crust.equals("Stuffed Crust")) {
            basePrice += 150; // Extra for stuffed crust
        }

        // Add price for each topping (e.g., LKR 50 per topping)
        double toppingPrice = this.toppings.size() * 50;
        return basePrice + toppingPrice;
    }

    // Getter for price
    public double getPrice() {
        return this.price;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public List<PizzaTopping> getToppings() {
        return toppings;
    }

    public void setToppings(List<PizzaTopping> toppings) {
        this.toppings = toppings;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    // Builder Pattern (PizzaBuilder)
    public static class PizzaBuilder {
        private PizzaSize size;
        private String crust;
        private String sauce;
        private List<PizzaTopping> toppings = new ArrayList<>();
        private String cheese;

        // Method to set the size
        public PizzaBuilder setSize(PizzaSize size) {
            this.size = size;
            return this;
        }

        // Method to set the crust
        public PizzaBuilder setCrust(String crust) {
            this.crust = crust;
            return this;
        }

        // Method to set the sauce
        public PizzaBuilder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        // Method to add a single topping
        public PizzaBuilder addTopping(PizzaTopping topping) {
            this.toppings.add(topping);
            return this;
        }

        // Method to add multiple toppings at once
        public PizzaBuilder addToppings(List<PizzaTopping> toppings) {
            this.toppings.addAll(toppings);
            return this;
        }

        // Method to set the cheese type
        public PizzaBuilder setCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        // Build and return a Pizza object
        public Pizza build() {
            return new Pizza(this);
        }
    }

    @Override
    public String toString() {
        return "Pizza [size=" + size + ", crust=" + crust + ", sauce=" + sauce + ", toppings=" + toppings + ", cheese=" + cheese + "]";
    }
}
