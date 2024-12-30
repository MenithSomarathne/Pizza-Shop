package model.promotion;

public class SeasonalPromotion implements PromotionStrategy {
    private double discountPercentage;

    public SeasonalPromotion(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double applyDiscount(double price) {
        return price - (price * (discountPercentage / 100));
    }
}