package model.promotion;

// No Promotion Strategy
public class NoPromotion implements PromotionStrategy {

    @Override
    public double applyDiscount(double price) {
        return price;
    }
}