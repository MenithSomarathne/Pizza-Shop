package model.order;

public class CompletedState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Order is completed. Thank you for your purchase!");
    }
}
