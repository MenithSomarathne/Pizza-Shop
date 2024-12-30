package model.promotion;

public class Promotion {
    private String name;
    private double discount;

    public Promotion(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }
}
