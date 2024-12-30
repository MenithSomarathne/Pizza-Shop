package model;

public class LoyaltyProgram {
    private int points;

    public LoyaltyProgram() {
        this.points = 0;
    }

    public void addPoints(int points) {
        this.points += points;
        System.out.println("Added " + points + " points to loyalty account.");
    }

    public int getPoints() {
        return points;
    }

    public void redeemPoints() {
        if (points >= 100) {
            // Redeem points for a discount or free pizza (mocking here)
            points -= 100;
            System.out.println("Redeemed 100 points for a free pizza or discount!");
        } else {
            System.out.println("Not enough points to redeem.");
        }
    }
}
