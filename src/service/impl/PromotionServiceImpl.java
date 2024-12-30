package service.impl;

import model.promotion.PromotionStrategy;
import service.PromotionService;

public class PromotionServiceImpl implements PromotionService {
    private PromotionStrategy promotionStrategy;

    public void setPromotionStrategy(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public double applyPromotion(double price) {
        return promotionStrategy.applyDiscount(price);
    }
}
