package model.order;

public class OrderedState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Order has been placed. Preparing the pizza...");
        // Transition to the next state
        order.setState(new PreparationState());
    }
}
